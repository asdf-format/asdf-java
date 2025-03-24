package org.asdfformat.asdf.ndarray;

/**
 * Slice of an n-dimensional array. Constructed by
 * methods in the {@link org.asdfformat.asdf.ndarray.Slices} class.
 */
public interface Slice {
    void validate(int originalLength);

    int computeNewOffset(int originalLength, int originalOffset, int originalStride);

    int computeNewLength(int originalLength);

    int computeNewStride(int originalStride);
}
