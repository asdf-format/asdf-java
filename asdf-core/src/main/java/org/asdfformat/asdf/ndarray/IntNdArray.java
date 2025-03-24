package org.asdfformat.asdf.ndarray;

/**
 * View of an n-dimensional array that returns
 * int elements.
 */
public interface IntNdArray extends NdArray<IntNdArray> {
    int get(int... indices);
}
