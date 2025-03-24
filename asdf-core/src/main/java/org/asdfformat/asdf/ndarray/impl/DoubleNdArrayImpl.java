package org.asdfformat.asdf.ndarray.impl;

import org.asdfformat.asdf.io.LowLevelFormat;
import org.asdfformat.asdf.ndarray.DataType;
import org.asdfformat.asdf.ndarray.DoubleNdArray;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class DoubleNdArrayImpl extends NdArrayBase<DoubleNdArray> implements DoubleNdArray {
    public DoubleNdArrayImpl(DataType dataType, int[] shape, ByteOrder byteOrder, int[] strides, int offset, int source, LowLevelFormat lowLevelFormat) {
        super(dataType, shape, byteOrder, strides, offset, source, lowLevelFormat);

        if (!dataType.isCompatibleWith(Double.TYPE)) {
            throw new IllegalStateException(String.format("Data type %s is not compatible with Java double", dataType));
        }
    }

    @Override
    protected DoubleNdArray newInstance(DataType dataType, int[] shape, ByteOrder byteOrder, int[] strides, int offset, int source, LowLevelFormat lowLevelFormat) {
        return new DoubleNdArrayImpl(dataType, shape, byteOrder, strides, offset, source, lowLevelFormat);
    }

    @Override
    protected String getClassName() {
        return "DoubleNdArray";
    }

    @Override
    public double get(int... indices) {
        final ByteBuffer byteBuffer = getByteBufferAt(indices);
        if (dataType == DataType.FLOAT64) {
            return byteBuffer.getDouble();
        } else if (dataType == DataType.FLOAT32) {
            return byteBuffer.getFloat();
        } else {
            throw new RuntimeException("Unhandled datatype: " + dataType);
        }
    }

    @Override
    public <ARRAY> ARRAY toArray(final ARRAY array) {
        final ArraySetter<double[]> setter;
        if (dataType == DataType.FLOAT64) {
            setter = (byteBuffer, arr, index, length) -> byteBuffer.asDoubleBuffer().get(arr, index, length);
        } else if (dataType == DataType.FLOAT32) {
            setter = (byteBuffer, arr, index, length) -> {
                final float[] floatArr = new float[length];
                byteBuffer.asFloatBuffer().get(floatArr);
                for (int i = 0; i < length; i++) {
                    arr[index + i] = floatArr[i];
                }
            };
        } else {
            throw new RuntimeException("Unhandled datatype: " + dataType);
        }

        return toArray(array, Double.TYPE, setter);
    }
}
