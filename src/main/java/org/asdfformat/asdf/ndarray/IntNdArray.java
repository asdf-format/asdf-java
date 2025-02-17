package org.asdfformat.asdf.ndarray;

/**
 * View of an n-dimensional array that returns
 * int elements.
 */
public interface IntNdArray extends NdArray<IntNdArray> {
    int get(long... indices);

    int[] toArray(int[] array);

    int[][] toArray(int[][] array);

    int[][][] toArray(int[][][] array);
}
