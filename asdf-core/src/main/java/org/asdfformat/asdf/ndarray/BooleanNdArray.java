package org.asdfformat.asdf.ndarray;

public interface BooleanNdArray extends NdArray<BooleanNdArray> {
    boolean get(int... indices);
}
