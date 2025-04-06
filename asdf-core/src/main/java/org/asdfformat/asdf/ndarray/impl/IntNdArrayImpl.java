package org.asdfformat.asdf.ndarray.impl;

import org.asdfformat.asdf.io.Block;
import org.asdfformat.asdf.ndarray.DataType;
import org.asdfformat.asdf.ndarray.IntNdArray;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class IntNdArrayImpl extends NdArrayBase<IntNdArray> implements IntNdArray {
    public IntNdArrayImpl(final DataType dataType, final int[] shape, final ByteOrder byteOrder, final int[] strides, final int offset, final Block block) {
        super(dataType, shape, byteOrder, strides, offset, block);

        if (!dataType.isCompatibleWith(Integer.TYPE)) {
            throw new IllegalStateException(String.format("Data type %s is not compatible with Java int", dataType));
        }
    }

    @Override
    protected IntNdArray newInstance(final DataType dataType, final int[] shape, final ByteOrder byteOrder, final int[] strides, final int offset, final Block block) {
        return new IntNdArrayImpl(dataType, shape, byteOrder, strides, offset, block);
    }

    @Override
    protected String getClassName() {
        return "IntNdArray";
    }

    @Override
    public int get(int... indices) {
        final ByteBuffer byteBuffer = getByteBufferAt(indices);
        if (dataType == DataType.INT8) {
            return byteBuffer.get();
        } else if (dataType == DataType.UINT8) {
            return byteBuffer.get() & 0xFF;
        } else if (dataType == DataType.INT16) {
            return byteBuffer.getShort();
        } else if (dataType == DataType.UINT16) {
            return byteBuffer.getShort() & 0xFFFF;
        } else if (dataType == DataType.INT32 || dataType == DataType.UINT32) {
            return byteBuffer.getInt();
        } else {
            throw new RuntimeException("Unhandled datatype: " + dataType);
        }
    }

    @Override
    public <ARRAY> ARRAY toArray(final ARRAY array) {
        final ArraySetter<int[]> setter;
        if (dataType == DataType.INT8) {
            setter = (byteBuffer, arr, index, length) -> {
                for (int i = index; i < length; i++) {
                    arr[i] = byteBuffer.get();
                }
            };
        } else if (dataType == DataType.UINT8) {
            setter = (byteBuffer, arr, index, length) -> {
                for (int i = index; i < length; i++) {
                    arr[i] = byteBuffer.get() & 0xFF;
                }
            };
        } else if (dataType == DataType.INT16) {
            setter = (byteBuffer, arr, index, length) -> {
                for (int i = index; i < length; i++) {
                    arr[i] = byteBuffer.getShort();
                }
            };
        } else if (dataType == DataType.UINT16) {
            setter = (byteBuffer, arr, index, length) -> {
                for (int i = index; i < length; i++) {
                    arr[i] = byteBuffer.getShort() & 0xFFFF;
                }
            };
        } else if (dataType == DataType.INT32 || dataType == DataType.UINT32) {
            setter = (byteBuffer, arr, index, length) -> byteBuffer.asIntBuffer().get(arr, index, length);
        } else {
            throw new RuntimeException("Unhandled datatype: " + dataType);
        }

        return toArray(array, Integer.TYPE, setter);
    }
}