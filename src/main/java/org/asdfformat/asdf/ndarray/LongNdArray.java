package org.asdfformat.asdf.ndarray;

/**
 * View of an n-dimensional array that returns
 * long elements.
 */
public interface LongNdArray extends NdArray<LongNdArray> {
    long get(long... indices);

    long[] toArray(long[] array);

    long[][] toArray(long[][] array);

    long[][][] toArray(long[][][] array);
}
