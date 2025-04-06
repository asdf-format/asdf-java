package org.asdfformat.asdf.io.impl;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.asdfformat.asdf.io.Block;
import org.asdfformat.asdf.io.compression.Compressor;
import org.asdfformat.asdf.io.compression.Compressors;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel.MapMode;
import java.util.Arrays;

@RequiredArgsConstructor
public class BlockV1_0_0 implements Block {
    public static final byte[] START_OF_BLOCK_SEQUENCE = { -45, 66, 76, 75 };
    private static final byte[] NO_COMPRESSION = { 0, 0, 0, 0 };

    public static Block fromFile(final RandomAccessFile file, final long headerPosition) throws IOException {
        file.seek(headerPosition + START_OF_BLOCK_SEQUENCE.length);

        final int headerSize = file.readUnsignedShort();

        final int flags = file.readInt();
        assert flags == 0;

        final byte[] compression = new byte[4];
        file.readFully(compression);

        final long allocatedSize = file.readLong();
        assert allocatedSize >= 0;

        final long usedSize = file.readLong();
        assert usedSize >= 0;

        final long dataSize = file.readLong();
        assert dataSize >= 0;

        final byte[] checksum = new byte[16];
        file.readFully(checksum);

        final long dataPosition = headerPosition + START_OF_BLOCK_SEQUENCE.length + 2 + headerSize;
        final long endPosition = dataPosition + allocatedSize;

        return new BlockV1_0_0(
            file,
            headerPosition,
            headerSize,
            compression,
            allocatedSize,
            usedSize,
            dataSize,
            checksum,
            dataPosition,
            endPosition
        );
    }

    private final RandomAccessFile file;
    private final long headerPosition;

    private final int headerSize;
    private final byte[] compression;
    private final long allocatedSize;
    private final long usedSize;
    private final long dataSize;
    private final byte[] checksum;

    private final long dataPosition;
    private final long endPosition;

    private RandomAccessFile decompressedFile;

    @Override
    public long getEndPosition() {
        return endPosition;
    }

    @Override
    public ByteBuffer getDataBuffer() {
        if (isCompressed()) {
            return getDecompressedDataBuffer();
        }
        
        return getOriginalDataBuffer();
    }

    @SneakyThrows(IOException.class)
    private ByteBuffer getOriginalDataBuffer() {
        return file.getChannel().map(MapMode.READ_ONLY, dataPosition, usedSize);
    }

    @SneakyThrows(IOException.class)
    public ByteBuffer getDecompressedDataBuffer() {
        if (decompressedFile == null) {
            final Compressor compressor = Compressors.of(compression);

            final File file = File.createTempFile("asdf-decompressed-block-", ".dat");
            file.deleteOnExit();

            try (final RandomAccessFile tempFile = new RandomAccessFile(file, "rw")) {
                compressor.decompress(getOriginalDataBuffer(), tempFile.getChannel().map(MapMode.READ_WRITE, 0, dataSize));
            }

            decompressedFile = new RandomAccessFile(file, "r");
        }

        return decompressedFile.getChannel().map(MapMode.READ_ONLY, 0, dataSize);
    }

    @Override
    public boolean isCompressed() {
        return !Arrays.equals(compression, NO_COMPRESSION);
    }
}
