package org.asdfformat.asdf.ndarray.impl;

import org.asdfformat.asdf.io.Block;
import org.asdfformat.asdf.ndarray.DataType;
import org.asdfformat.asdf.ndarray.DataTypes;
import org.asdfformat.asdf.ndarray.DoubleNdArray;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import static org.asdfformat.asdf.ndarray.impl.Float16Utils.float16ToFloat;

public class DoubleNdArrayImpl extends NdArrayBase<DoubleNdArray> implements DoubleNdArray {
    public DoubleNdArrayImpl(final DataType dataType, final int[] shape, final ByteOrder byteOrder, final int[] strides, final int offset, final Block block) {
        super(dataType, shape, byteOrder, strides, offset, block);

        if (!dataType.isCompatibleWith(Double.TYPE)) {
            throw new IllegalStateException(String.format("Data type %s is not compatible with Java double", dataType));
        }
    }

    @Override
    protected DoubleNdArray newInstance(final DataType dataType, final int[] shape, final ByteOrder byteOrder, final int[] strides, final int offset, final Block block) {
        return new DoubleNdArrayImpl(dataType, shape, byteOrder, strides, offset, block);
    }

    @Override
    protected String getClassName() {
        return "DoubleNdArray";
    }

    @Override
    public double get(int... indices) {
        final ByteBuffer byteBuffer = getByteBufferAt(indices);
        if (dataType.equals(DataTypes.FLOAT64)) {
            return byteBuffer.getDouble();
        } else if (dataType.equals(DataTypes.FLOAT32)) {
            return byteBuffer.getFloat();
        } else if (dataType.equals(DataTypes.FLOAT16)) {
            return float16ToFloat(byteBuffer.getShort());
        } else {
            throw new RuntimeException("Unhandled datatype: " + dataType);
        }
    }

    @Override
    public <ARRAY> ARRAY toArray(final ARRAY array) {
        final ArraySetter<double[]> setter;
        if (dataType.equals(DataTypes.FLOAT64)) {
            setter = (byteBuffer, arr, index, length) -> byteBuffer.asDoubleBuffer().get(arr, index, length);
        } else if (dataType.equals(DataTypes.FLOAT32)) {
            setter = (byteBuffer, arr, index, length) -> {
                final float[] floatArr = new float[length];
                byteBuffer.asFloatBuffer().get(floatArr);
                for (int i = 0; i < length; i++) {
                    arr[index + i] = floatArr[i];
                }
            };
        } else if (dataType.equals(DataTypes.FLOAT16)) {
            setter = (byteBuffer, arr, index, length) -> {
                for (int i = 0; i < length; i++) {
                    arr[index + i] = float16ToFloat(byteBuffer.getShort());
                }
            };
        } else {
            throw new RuntimeException("Unhandled datatype: " + dataType);
        }

        return toArray(array, Double.TYPE, setter);
    }
}
