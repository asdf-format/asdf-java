package org.asdfformat.asdf.ndarray.impl;

import org.asdfformat.asdf.io.LowLevelFormat;
import org.asdfformat.asdf.ndarray.DataType;
import org.asdfformat.asdf.ndarray.FloatNdArray;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class FloatNdArrayImpl extends NdArrayBase<FloatNdArray> implements FloatNdArray {
    public FloatNdArrayImpl(final DataType dataType, final int[] shape, final ByteOrder byteOrder, final int[] strides, final int offset, final int source, final LowLevelFormat lowLevelFormat) {
        super(dataType, shape, byteOrder, strides, offset, source, lowLevelFormat);

        if (!dataType.isCompatibleWith(Float.TYPE)) {
            throw new IllegalStateException(String.format("Data type %s is not compatible with Java float", dataType));
        }
    }

    @Override
    protected FloatNdArray newInstance(final DataType dataType, final int[] shape, final ByteOrder byteOrder, final int[] strides, final int offset, final int source, final LowLevelFormat lowLevelFormat) {
        return new FloatNdArrayImpl(dataType, shape, byteOrder, strides, offset, source, lowLevelFormat);
    }

    @Override
    protected String getClassName() {
        return "FloatNdArray";
    }

    @Override
    public float get(int... indices) {
        final ByteBuffer byteBuffer = getByteBufferAt(indices);
        if (dataType == DataType.FLOAT32) {
            return byteBuffer.getFloat();
        } else {
            throw new RuntimeException("Unhandled datatype: " + dataType);
        }
    }

    @Override
    public <ARRAY> ARRAY toArray(final ARRAY array) {
        final ArraySetter<float[]> setter = (byteBuffer, arr, index, length) -> byteBuffer.asFloatBuffer().get(arr, index, length);
        return toArray(array, Float.TYPE, setter);
    }
}
