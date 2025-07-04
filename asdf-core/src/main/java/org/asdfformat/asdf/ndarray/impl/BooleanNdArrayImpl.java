package org.asdfformat.asdf.ndarray.impl;

import org.asdfformat.asdf.io.Block;
import org.asdfformat.asdf.ndarray.BooleanNdArray;
import org.asdfformat.asdf.ndarray.DataType;
import org.asdfformat.asdf.ndarray.NdArray;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class BooleanNdArrayImpl extends NdArrayBase<BooleanNdArray> implements BooleanNdArray {
    public BooleanNdArrayImpl(final DataType dataType, final int[] shape, final ByteOrder byteOrder, final int[] strides, final int offset, final Block block) {
        super(dataType, shape, byteOrder, strides, offset, block);

        if (!dataType.isCompatibleWith(Boolean.TYPE)) {
            throw new IllegalStateException(String.format("Data type %s is not compatible with Java boolean", dataType));
        }
    }

    @Override
    public boolean get(final int... indices) {
        final ByteBuffer byteBuffer = getByteBufferAt(indices);
        return byteBuffer.get() != 0;
    }

    @Override
    protected NdArray<BooleanNdArray> newInstance(final DataType dataType, final int[] shape, final ByteOrder byteOrder, final int[] strides, final int offset, final Block block) {
        return new BooleanNdArrayImpl(dataType, shape, byteOrder, strides, offset, block);
    }

    @Override
    protected String getClassName() {
        return "BooleanNdArray";
    }

    @Override
    public <ARRAY> ARRAY toArray(final ARRAY array) {
        final ArraySetter<boolean[]> setter = (byteBuffer, arr, index, length) -> {
            for (int i = index; i < length; i++) {
                arr[i] = byteBuffer.get() != 0;
            }
        };

        return toArray(array, Boolean.TYPE, setter);
    }
}
