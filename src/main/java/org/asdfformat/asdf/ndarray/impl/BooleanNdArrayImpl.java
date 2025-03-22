package org.asdfformat.asdf.ndarray.impl;

import org.asdfformat.asdf.io.LowLevelFormat;
import org.asdfformat.asdf.ndarray.BooleanNdArray;
import org.asdfformat.asdf.ndarray.DataType;

import java.nio.ByteOrder;

public class BooleanNdArrayImpl extends NdArrayBase<BooleanNdArray> implements BooleanNdArray{
    public BooleanNdArrayImpl(final DataType dataType, final int[] shape, final ByteOrder byteOrder, final int[] strides, final int offset, final int source, final LowLevelFormat lowLevelFormat) {
        super(dataType, shape, byteOrder, strides, offset, source, lowLevelFormat);
    }

    @Override
    protected BooleanNdArray newInstance(final DataType dataType, final int[] shape, final ByteOrder byteOrder, final int[] strides, final int offset, final int source, final LowLevelFormat lowLevelFormat) {
        return new BooleanNdArrayImpl(dataType, shape, byteOrder, strides, offset, source, lowLevelFormat);
    }

    @Override
    protected String getClassName() {
        return "BooleanNdArray";
    }

    @Override
    public boolean get(final int... indices) {
        return false;
    }

    @Override
    public <ARRAY> ARRAY toArray(final ARRAY array) {
        return null;
    }
}
