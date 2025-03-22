package org.asdfformat.asdf.ndarray.impl;

import org.asdfformat.asdf.io.LowLevelFormat;
import org.asdfformat.asdf.ndarray.BigIntegerNdArray;
import org.asdfformat.asdf.ndarray.DataType;

import java.math.BigInteger;
import java.nio.ByteOrder;

public class BigIntegerNdArrayImpl extends NdArrayBase<BigIntegerNdArray> implements BigIntegerNdArray {
    public BigIntegerNdArrayImpl(final DataType dataType, final int[] shape, final ByteOrder byteOrder, final int[] strides, final int offset, final int source, final LowLevelFormat lowLevelFormat) {
        super(dataType, shape, byteOrder, strides, offset, source, lowLevelFormat);
    }

    @Override
    protected BigIntegerNdArray newInstance(final DataType dataType, final int[] shape, final ByteOrder byteOrder, final int[] strides, final int offset, final int source, final LowLevelFormat lowLevelFormat) {
        return new BigIntegerNdArrayImpl(dataType, shape, byteOrder, strides, offset, source, lowLevelFormat);
    }

    @Override
    protected String getClassName() {
        return "BigIntegerNdArray";
    }

    @Override
    public BigInteger get(final int... indices) {
        return null;
    }

    @Override
    public <ARRAY> ARRAY toArray(final ARRAY array) {
        return null;
    }
}
