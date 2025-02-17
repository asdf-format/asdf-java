package org.asdfformat.asdf.ndarray;

/**
 * View of an n-dimensional array that returns
 * array elements as shorts.
 */
public interface ShortNdArray extends NdArray<ShortNdArray> {
    short get(long... indices);

    short[] toArray(short[] array);

    short[][] toArray(short[][] array);

    short[][][] toArray(short[][][] array);
}
