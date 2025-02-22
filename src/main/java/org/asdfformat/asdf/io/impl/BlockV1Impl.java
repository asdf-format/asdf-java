package org.asdfformat.asdf.io.impl;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel.MapMode;

import org.asdfformat.asdf.io.Block;

public class BlockV1Impl implements Block {
    public static final byte[] START_OF_BLOCK_SEQUENCE = { -45, 66, 76, 75 };
    private static final byte[] NO_COMPRESSION = { 0, 0, 0, 0 };

    public static Block fromFile(final RandomAccessFile file, final long headerPosition) throws IOException {
        file.seek(headerPosition + START_OF_BLOCK_SEQUENCE.length);

        final int headerSize = file.readUnsignedShort();

        final int flags = file.readInt();
        assert flags == 0;

        final byte[] compression = new byte[4];
        file.readFully(new byte[4]);

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

        return new BlockV1Impl(
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

    public BlockV1Impl(final RandomAccessFile file, final long headerPosition, final int headerSize, final byte[] compression, final long allocatedSize, final long usedSize, final long dataSize, final byte[] checksum, final long dataPosition, final long endPosition) {
        this.file = file;
        this.headerPosition = headerPosition;
        this.headerSize = headerSize;
        this.compression = compression;
        this.allocatedSize = allocatedSize;
        this.usedSize = usedSize;
        this.dataSize = dataSize;
        this.checksum = checksum;
        this.dataPosition = dataPosition;
        this.endPosition = endPosition;
    }

    @Override
    public long getEndPosition() {
        return endPosition;
    }

    @Override
    public ByteBuffer getDataBuffer() throws IOException {
        return file.getChannel().map(MapMode.READ_ONLY, dataPosition, usedSize);
    }
}
