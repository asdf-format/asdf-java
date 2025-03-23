package org.asdfformat.asdf.ndarray.impl;

import java.nio.ByteBuffer;

public class ByteBufferUtils {
    public static void getReverse(final ByteBuffer byteBuffer, final byte[] dest) {
        for (int i = dest.length - 1; i >= 0; i--) {
            dest[i] = byteBuffer.get();
        }
    }

    public static void getReverse(final ByteBuffer byteBuffer, final byte[] dest, final int offset, final int length) {
        for (int i = offset + length - 1; i >= offset; i--) {
            dest[i] = byteBuffer.get();
        }
    }
}
