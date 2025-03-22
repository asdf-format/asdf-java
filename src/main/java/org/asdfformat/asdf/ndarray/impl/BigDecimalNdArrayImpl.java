package org.asdfformat.asdf.ndarray.impl;

import org.asdfformat.asdf.io.LowLevelFormat;
import org.asdfformat.asdf.ndarray.BigDecimalNdArray;
import org.asdfformat.asdf.ndarray.DataType;

import java.math.BigDecimal;
import java.nio.ByteOrder;

public class BigDecimalNdArrayImpl extends NdArrayBase<BigDecimalNdArray> implements BigDecimalNdArray {
    public BigDecimalNdArrayImpl(final DataType dataType, final int[] shape, final ByteOrder byteOrder, final int[] strides, final int offset, final int source, final LowLevelFormat lowLevelFormat) {
        super(dataType, shape, byteOrder, strides, offset, source, lowLevelFormat);
    }

    @Override
    protected BigDecimalNdArray newInstance(final DataType dataType, final int[] shape, final ByteOrder byteOrder, final int[] strides, final int offset, final int source, final LowLevelFormat lowLevelFormat) {
        return new BigDecimalNdArrayImpl(dataType, shape, byteOrder, strides, offset, source, lowLevelFormat);
    }

    @Override
    protected String getClassName() {
        return "BigDecimalNdArray";
    }

    @Override
    public BigDecimal get(final int... indices) {
        return null;
    }


    @Override
    public <ARRAY> ARRAY toArray(final ARRAY array) {
        return null;
    }
}
