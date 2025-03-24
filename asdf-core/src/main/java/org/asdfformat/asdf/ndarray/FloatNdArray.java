package org.asdfformat.asdf.ndarray;

/**
 * View of an n-dimensional array that returns
 * float elements.
 */
public interface FloatNdArray extends NdArray<FloatNdArray> {
    float get(int... indices);
}
