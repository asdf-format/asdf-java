package org.asdfformat.asdf.io.compression;

import org.apache.commons.compress.compressors.bzip2.BZip2CompressorInputStream;
import org.asdfformat.asdf.io.util.IOUtils;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public class BZip2Compressor implements Compressor {
    private static final byte[] IDENTIFIER = "bzp2".getBytes(StandardCharsets.UTF_8);

    @Override
    public byte[] getIdentifier() {
        return IDENTIFIER;
    }

    @Override
    public long decompress(final ByteBuffer inputBuffer, final ByteBuffer outputBuffer) throws IOException {
        try (
            final ByteBufferInputStream byteBufferInputStream = new ByteBufferInputStream(inputBuffer);
            final BZip2CompressorInputStream bZip2CompressorInputStream = new BZip2CompressorInputStream(byteBufferInputStream)
        ) {
            return IOUtils.transferTo(bZip2CompressorInputStream, outputBuffer);
        }
    }
}
