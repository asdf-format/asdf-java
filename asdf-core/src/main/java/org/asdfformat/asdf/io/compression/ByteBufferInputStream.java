package org.asdfformat.asdf.io.compression;

import java.io.InputStream;
import java.nio.ByteBuffer;

public class ByteBufferInputStream extends InputStream {
    private final ByteBuffer buffer;
    private int mark;

    ByteBufferInputStream(final ByteBuffer buffer) {
        this.buffer = buffer.duplicate();
        this.mark = this.buffer.position();
    }

    @Override
    public int read() {
        if (!buffer.hasRemaining()) {
            return -1;
        }

        return buffer.get() & 0xFF;
    }

    @Override
    public int read(final byte[] bytes, final int offset, final int length) {
        if (length == 0) {
            return 0;
        }

        final int remaining = buffer.remaining();
        if (remaining <= 0) {
            return -1;
        }

        final int bytesRead = Math.min(remaining, length);
        buffer.get(bytes, offset, bytesRead);

        return bytesRead;
    }

    @Override
    public long skip(final long n) {
        if (n == 0) {
            return 0;
        }

        if (buffer.remaining() <= 0) {
            return -1;
        }

        final int bytesSkipped = (int) Math.min(buffer.remaining(), n);
        buffer.position(buffer.position() + bytesSkipped);

        return bytesSkipped;
    }

    @Override
    public void mark(final int readlimit) {
        this.mark = buffer.position();
    }

    @Override
    public void reset() {
        buffer.position(mark);
    }

    @Override
    public boolean markSupported() {
        return true;
    }

    @Override
    public int available() {
        return buffer.remaining();
    }
}
