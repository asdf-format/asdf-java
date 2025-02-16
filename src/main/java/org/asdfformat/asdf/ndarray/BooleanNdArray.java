package org.asdfformat.asdf.ndarray;

/**
 * View of an n-dimensional array that returns
 * boolean elements.
 */
public interface BooleanNdArray extends NdArray<BooleanNdArray> {
    boolean getScalar(long... indices);

    boolean[] toArray(boolean[] array);

    boolean[][] toArray(boolean[][] array);

    boolean[][][] toArray(boolean[][][] array);
}
