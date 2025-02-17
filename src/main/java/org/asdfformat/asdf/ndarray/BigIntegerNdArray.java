package org.asdfformat.asdf.ndarray;

import java.math.BigInteger;

/**
 * View of an n-dimensional array that returns
 * BigInteger elements.
 */
public interface BigIntegerNdArray extends NdArray<BigIntegerNdArray> {
    BigInteger get(long... indices);

    BigInteger[] toArray(BigInteger[] array);

    BigInteger[][] toArray(BigInteger[][] array);

    BigInteger[][][] toArray(BigInteger[][][] array);
}
