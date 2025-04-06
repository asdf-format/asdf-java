package org.asdfformat.asdf.io.util;

import java.io.DataInput;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;

public class IOUtils {
    private static final int DEFAULT_BUFFER_SIZE = 16384;

    private static final OutputStream NULL_OUTPUT_STREAM = new NullOutputStream();

    public static long readUntil(final DataInput input, final byte[] endSequence, final OutputStream outputStream, final long limit) throws IOException {
        assert endSequence.length > 0;

        boolean inSequence = false;
        int nextSequenceIndex = 0;
        long bytesRead = 0;

        try {
            while (true) {
                if (bytesRead >= limit) {
                    return -1;
                }

                final byte value = input.readByte();
                bytesRead++;
                outputStream.write(value);

                if (inSequence) {
                    if (value == endSequence[nextSequenceIndex]) {
                        nextSequenceIndex++;
                        if (nextSequenceIndex >= endSequence.length) {
                            break;
                        }
                    } else {
                        inSequence = false;
                        nextSequenceIndex = 0;
                    }
                } else if (value == endSequence[nextSequenceIndex]) {
                    inSequence = true;
                    nextSequenceIndex++;
                    if (nextSequenceIndex >= endSequence.length) {
                        break;
                    }
                }
            }
        } catch (final EOFException e) {
            return -1;
        }

        return bytesRead;
    }

    public static long seekUntil(final DataInput input, final byte[] endSequence) throws IOException {
        return readUntil(input, endSequence, NULL_OUTPUT_STREAM, Long.MAX_VALUE);
    }

    public static long seekUntil(final DataInput input, final byte[] endSequence, final long limit) throws IOException {
        return readUntil(input, endSequence, NULL_OUTPUT_STREAM, limit);
    }

    public static void closeQuietly(final AutoCloseable autoCloseable) {
        try {
            autoCloseable.close();
        } catch (final Exception ignored) {

        }
    }

    public static long transferTo(final InputStream inputStream, final ByteBuffer outputBuffer) throws IOException {
        long bytesTransferred = 0;
        final byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
        int bytesRead;

        while ((bytesRead = inputStream.read(buffer, 0, DEFAULT_BUFFER_SIZE)) >= 0) {
            outputBuffer.put(buffer, 0, bytesRead);
            bytesTransferred += bytesRead;
        }

        return bytesTransferred;
    }
}
