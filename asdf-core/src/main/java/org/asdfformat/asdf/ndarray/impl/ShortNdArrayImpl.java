package org.asdfformat.asdf.ndarray.impl;

import org.asdfformat.asdf.io.Block;
import org.asdfformat.asdf.ndarray.DataType;
import org.asdfformat.asdf.ndarray.DataTypes;
import org.asdfformat.asdf.ndarray.ShortNdArray;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class ShortNdArrayImpl extends NdArrayBase<ShortNdArray> implements ShortNdArray {
    public ShortNdArrayImpl(final DataType dataType, final int[] shape, final ByteOrder byteOrder, final int[] strides, final int offset, final Block block) {
        super(dataType, shape, byteOrder, strides, offset, block);

        if (!dataType.isCompatibleWith(Short.TYPE)) {
            throw new IllegalStateException(String.format("Data type %s is not compatible with Java short", dataType));
        }
    }

    @Override
    protected ShortNdArray newInstance(final DataType dataType, final int[] shape, final ByteOrder byteOrder, final int[] strides, final int offset, final Block block) {
        return new ShortNdArrayImpl(dataType, shape, byteOrder, strides, offset, block);
    }

    @Override
    protected String getClassName() {
        return "ShortNdArray";
    }

    @Override
    public short get(int... indices) {
        final ByteBuffer byteBuffer = getByteBufferAt(indices);
        if (dataType.equals(DataTypes.INT8)) {
            return byteBuffer.get();
        } else if (dataType.equals(DataTypes.UINT8)) {
            return (short) (byteBuffer.get() & 0xFF);
        } else if (dataType.equals(DataTypes.INT16) || dataType.equals(DataTypes.UINT16)) {
            return byteBuffer.getShort();
        } else {
            throw new RuntimeException("Unhandled datatype: " + dataType);
        }
    }

    @Override
    public <ARRAY> ARRAY toArray(final ARRAY array) {
        final ArraySetter<short[]> setter;
        if (dataType.equals(DataTypes.INT8)) {
            setter = (byteBuffer, arr, index, length) -> {
                for (int i = index; i < length; i++) {
                    arr[i] = byteBuffer.get();
                }
            };
        } else if (dataType.equals(DataTypes.UINT8)) {
            setter = (byteBuffer, arr, index, length) -> {
                for (int i = index; i < length; i++) {
                    arr[i] = (short) (byteBuffer.get() & 0xFF);
                }
            };
        } else if (dataType.equals(DataTypes.INT16) || dataType.equals(DataTypes.UINT16)) {
            setter = (byteBuffer, arr, index, length) -> byteBuffer.asShortBuffer().get(arr, index, length);
        } else {
            throw new RuntimeException("Unhandled datatype: " + dataType);
        }

        return toArray(array, Short.TYPE, setter);
    }
}
