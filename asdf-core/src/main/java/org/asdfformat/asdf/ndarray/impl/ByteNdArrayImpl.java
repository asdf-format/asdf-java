package org.asdfformat.asdf.ndarray.impl;

import org.asdfformat.asdf.io.Block;
import org.asdfformat.asdf.ndarray.ByteNdArray;
import org.asdfformat.asdf.ndarray.DataType;
import org.asdfformat.asdf.ndarray.DataTypes;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class ByteNdArrayImpl extends NdArrayBase<ByteNdArray> implements ByteNdArray {
    public ByteNdArrayImpl(final DataType dataType, final int[] shape, final ByteOrder byteOrder, final int[] strides, final int offset, final Block block) {
        super(dataType, shape, byteOrder, strides, offset, block);

        if (!dataType.isCompatibleWith(Byte.TYPE)) {
            throw new IllegalStateException(String.format("Data type %s is not compatible with Java byte", dataType));
        }
    }

    @Override
    protected ByteNdArray newInstance(final DataType dataType, final int[] shape, final ByteOrder byteOrder, final int[] strides, final int offset, final Block block) {
        return new ByteNdArrayImpl(dataType, shape, byteOrder, strides, offset, block);
    }

    @Override
    protected String getClassName() {
        return "ByteNdArray";
    }

    @Override
    public byte get(int... indices) {
        final ByteBuffer byteBuffer = getByteBufferAt(indices);
        if (dataType.equals(DataTypes.INT8) || dataType.equals(DataTypes.UINT8)) {
            return byteBuffer.get();
        } else {
            throw new RuntimeException("Unhandled datatype: " + dataType);
        }
    }

    @Override
    public <ARRAY> ARRAY toArray(final ARRAY array) {
        final ArraySetter<byte[]> setter = ByteBuffer::get;
        return toArray(array, Byte.TYPE, setter);
    }
}
