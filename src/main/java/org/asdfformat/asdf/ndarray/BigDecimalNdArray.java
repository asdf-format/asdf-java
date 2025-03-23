package org.asdfformat.asdf.ndarray;

import java.math.BigDecimal;

/**
 * View of an n-dimensional array that returns
 * BigDecimal elements.
 */
public interface BigDecimalNdArray extends NdArray<BigDecimalNdArray> {
    BigDecimal get(int... indices);
}
