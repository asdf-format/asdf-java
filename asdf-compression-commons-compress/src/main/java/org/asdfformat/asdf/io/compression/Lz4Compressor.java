package org.asdfformat.asdf.io.compression;

import org.apache.commons.compress.compressors.lz4.BlockLZ4CompressorInputStream;
import org.asdfformat.asdf.io.util.IOUtils;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class Lz4Compressor implements Compressor {
    public static final byte[] IDENTIFIER = {108, 122, 52, 0}; // 'lz4' + padding

    @Override
    public byte[] getIdentifier() {
        return IDENTIFIER;
    }

    @Override
    public long decompress(final ByteBuffer inputBuffer, final ByteBuffer outputBuffer) throws IOException {
        long bytesDecompressed = 0L;

        while (inputBuffer.hasRemaining()) {
            inputBuffer.order(ByteOrder.BIG_ENDIAN);
            final int lz4BlockLength = inputBuffer.getInt() - 4;
            if (lz4BlockLength < 0) {
                throw new RuntimeException("LZ4 block length > " + Integer.MAX_VALUE + " not supported");
            }

            // Discard the uncompressed data size written by the Python LZ4 bindings:
            inputBuffer.getInt();

            final ByteBuffer lz4BlockInputBuffer = inputBuffer.duplicate();
            lz4BlockInputBuffer.limit(inputBuffer.position() + lz4BlockLength);

            try (final ByteBufferInputStream byteBufferInputStream = new ByteBufferInputStream(lz4BlockInputBuffer);
                 final BlockLZ4CompressorInputStream blockLZ4CompressorInputStream = new BlockLZ4CompressorInputStream(byteBufferInputStream)
            ) {
                bytesDecompressed += IOUtils.transferTo(blockLZ4CompressorInputStream, outputBuffer);
            }

            inputBuffer.position(inputBuffer.position() + lz4BlockLength);
        }

        return bytesDecompressed;
    }
}
