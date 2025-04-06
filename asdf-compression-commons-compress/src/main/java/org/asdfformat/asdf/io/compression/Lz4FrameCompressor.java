package org.asdfformat.asdf.io.compression;

import org.apache.commons.compress.compressors.lz4.FramedLZ4CompressorInputStream;
import org.asdfformat.asdf.io.util.IOUtils;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public class Lz4FrameCompressor implements Compressor {
    public static final byte[] IDENTIFIER = "lz4f".getBytes(StandardCharsets.UTF_8);

    @Override
    public byte[] getIdentifier() {
        return IDENTIFIER;
    }

    @Override
    public long decompress(final ByteBuffer inputBuffer, final ByteBuffer outputBuffer) throws IOException {
        try (
                final ByteBufferInputStream byteBufferInputStream = new ByteBufferInputStream(inputBuffer);
                final FramedLZ4CompressorInputStream framedLZ4CompressorInputStream = new FramedLZ4CompressorInputStream(byteBufferInputStream)
        ) {
            return IOUtils.transferTo(framedLZ4CompressorInputStream, outputBuffer);
        }
    }
}
