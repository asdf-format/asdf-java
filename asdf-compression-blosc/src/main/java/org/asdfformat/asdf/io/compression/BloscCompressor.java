package org.asdfformat.asdf.io.compression;

import org.blosc.JBlosc;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public class BloscCompressor implements Compressor {
    private static final byte[] IDENTIFIER = "blsc".getBytes(StandardCharsets.UTF_8);
    private static final int NUM_THREADS = 1;

    @Override
    public byte[] getIdentifier() {
        return IDENTIFIER;
    }

    @Override
    public long decompress(final ByteBuffer inputBuffer, final ByteBuffer outputBuffer) throws IOException {
        final long bytesRead = outputBuffer.remaining();

        JBlosc.decompressCtx(inputBuffer, outputBuffer, bytesRead, NUM_THREADS);

        return bytesRead;
    }
}
