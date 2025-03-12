package org.asdfformat.asdf.ndarray.impl;

import java.nio.ByteOrder;

import org.asdfformat.asdf.io.LowLevelFormat;
import org.asdfformat.asdf.ndarray.DataType;
import org.asdfformat.asdf.ndarray.NdArray;


public class NdArrayImpl extends NdArrayBase<NdArray<?>> implements NdArray<NdArray<?>> {
    public NdArrayImpl(DataType dataType, int[] shape, ByteOrder byteOrder, int[] strides, int offset, int source, LowLevelFormat lowLevelFormat) {
        super(dataType, shape, byteOrder, strides, offset, source, lowLevelFormat);
    }

    @Override
    protected NdArray<?> newInstance(DataType dataType, int[] shape, ByteOrder byteOrder, int[] strides, int offset, int source, LowLevelFormat lowLevelFormat) {
        return new NdArrayImpl(dataType, shape, byteOrder, strides, offset, source, lowLevelFormat);
    }
}
