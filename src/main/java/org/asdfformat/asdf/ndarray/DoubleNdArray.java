package org.asdfformat.asdf.ndarray;

/**
 * View of an n-dimensional array that returns
 * double elements.
 */
public interface DoubleNdArray extends NdArray<DoubleNdArray> {
    double getScalar(long... indices);

    double[] toArray(double[] array);

    double[][] toArray(double[][] array);

    double[][][] toArray(double[][][] array);
}
