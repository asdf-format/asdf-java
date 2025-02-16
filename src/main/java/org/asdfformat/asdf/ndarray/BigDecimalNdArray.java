package org.asdfformat.asdf.ndarray;

import java.math.BigDecimal;

/**
 * View of an n-dimensional array that returns
 * BigDecimal elements.
 */
public interface BigDecimalNdArray extends NdArray<BigDecimalNdArray> {
    BigDecimal getScalar(long... indices);

    BigDecimal[] toArray(BigDecimal[] array);

    BigDecimal[][] toArray(BigDecimal[][] array);

    BigDecimal[][][] toArray(BigDecimal[][][] array);
}
