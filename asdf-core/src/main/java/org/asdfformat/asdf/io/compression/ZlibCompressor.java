package org.asdfformat.asdf.io.compression;

import org.asdfformat.asdf.io.util.IOUtils;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.zip.InflaterInputStream;

public class ZlibCompressor implements Compressor {
    private static final byte[] IDENTIFIER = "zlib".getBytes(StandardCharsets.UTF_8);

    @Override
    public byte[] getIdentifier() {
        return IDENTIFIER;
    }

    @Override
    public long decompress(final ByteBuffer inputBuffer, final ByteBuffer outputBuffer) throws IOException {
        try (
                final ByteBufferInputStream byteBufferInputStream = new ByteBufferInputStream(inputBuffer);
                final InflaterInputStream inflaterInputStream = new InflaterInputStream(byteBufferInputStream)
        ) {
            return IOUtils.transferTo(inflaterInputStream, outputBuffer);
        }
    }
}
