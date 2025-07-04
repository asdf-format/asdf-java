package org.asdfformat.asdf.ndarray;

public interface StringNdArray extends NdArray<StringNdArray> {
    String get(int... indices);
}
