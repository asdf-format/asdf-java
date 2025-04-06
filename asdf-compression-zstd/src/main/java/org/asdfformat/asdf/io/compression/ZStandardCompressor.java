package org.asdfformat.asdf.io.compression;

import com.github.luben.zstd.ZstdInputStream;
import org.asdfformat.asdf.io.util.IOUtils;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public class ZStandardCompressor implements Compressor {
    private static final byte[] IDENTIFIER = "zstd".getBytes(StandardCharsets.UTF_8);

    @Override
    public byte[] getIdentifier() {
        return IDENTIFIER;
    }

    @Override
    public long decompress(final ByteBuffer inputBuffer, final ByteBuffer outputBuffer) throws IOException {
        try (
                final ByteBufferInputStream byteBufferInputStream = new ByteBufferInputStream(inputBuffer);
                final ZstdInputStream zstdInputStream = new ZstdInputStream(byteBufferInputStream)
        ) {
            return IOUtils.transferTo(zstdInputStream, outputBuffer);
        }
    }
}
