package org.asdfformat.asdf.ndarray.impl;

import org.asdfformat.asdf.io.Block;
import org.asdfformat.asdf.ndarray.DataType;
import org.asdfformat.asdf.ndarray.LongNdArray;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class LongNdArrayImpl extends NdArrayBase<LongNdArray> implements LongNdArray {
    public LongNdArrayImpl(final DataType dataType, final int[] shape, final ByteOrder byteOrder, final int[] strides, final int offset, final Block block) {
        super(dataType, shape, byteOrder, strides, offset, block);

        if (!dataType.isCompatibleWith(Long.TYPE)) {
            throw new IllegalStateException(String.format("Data type %s is not compatible with Java long", dataType));
        }
    }

    @Override
    protected LongNdArray newInstance(final DataType dataType, final int[] shape, final ByteOrder byteOrder, final int[] strides, final int offset, final Block block) {
        return new LongNdArrayImpl(dataType, shape, byteOrder, strides, offset, block);
    }

    @Override
    protected String getClassName() {
        return "LongNdArray";
    }

    @Override
    public long get(int... indices) {
        final ByteBuffer byteBuffer = getByteBufferAt(indices);
        if (dataType == DataType.INT8) {
            return byteBuffer.get();
        } else if (dataType == DataType.UINT8) {
            return byteBuffer.get() & 0xFF;
        } else if (dataType == DataType.INT16) {
            return byteBuffer.getShort();
        } else if (dataType == DataType.UINT16) {
            return byteBuffer.getShort() & 0xFFFF;
        } else if (dataType == DataType.INT32) {
            return byteBuffer.getInt();
        } else if (dataType == DataType.UINT32) {
            return byteBuffer.getInt() & 0xFFFFFFFFL;
        } else if (dataType == DataType.INT64 || dataType == DataType.UINT64) {
            return byteBuffer.getLong();
        } else {
            throw new RuntimeException("Unhandled datatype: " + dataType);
        }
    }

    @Override
    public <ARRAY> ARRAY toArray(final ARRAY array) {
        final ArraySetter<long[]> setter;
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
        } else if (dataType == DataType.INT32) {
            setter = (byteBuffer, arr, index, length) -> {
                for (int i = index; i < length; i++) {
                    arr[i] = byteBuffer.getInt();
                }
            };
        } else if (dataType == DataType.UINT32) {
            setter = (byteBuffer, arr, index, length) -> {
                for (int i = index; i < length; i++) {
                    arr[i] = byteBuffer.getInt() & 0xFFFFFFFFL;
                }
            };
        } else if (dataType == DataType.INT64 || dataType == DataType.UINT64) {
            setter = (byteBuffer, arr, index, length) -> byteBuffer.asLongBuffer().get(arr, index, length);
        } else {
            throw new RuntimeException("Unhandled datatype: " + dataType);
        }

        return toArray(array, Long.TYPE, setter);
    }
}
