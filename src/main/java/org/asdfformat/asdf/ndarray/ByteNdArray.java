package org.asdfformat.asdf.ndarray;

/**
 * View of an n-dimensional array that returns
 * byte elements.
 */
public interface ByteNdArray extends NdArray<ByteNdArray> {
    byte getScalar(long... indices);

    byte[] toArray(byte[] array);

    byte[][] toArray(byte[][] array);

    byte[][][] toArray(byte[][][] array);
}
