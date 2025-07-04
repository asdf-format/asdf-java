package org.asdfformat.asdf.ndarray.impl;

import org.asdfformat.asdf.io.Block;
import org.asdfformat.asdf.ndarray.DataType;
import org.asdfformat.asdf.ndarray.DataTypeFamilyType;
import org.asdfformat.asdf.ndarray.NdArray;
import org.asdfformat.asdf.ndarray.StringNdArray;
import org.asdfformat.asdf.util.AsdfCharsets;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class StringNdArrayImpl extends NdArrayBase<StringNdArray> implements StringNdArray {
    public StringNdArrayImpl(final DataType dataType, final int[] shape, final ByteOrder byteOrder, final int[] strides, final int offset, final Block block) {
        super(dataType, shape, byteOrder, strides, offset, block);

        if (!dataType.isCompatibleWith(String.class)) {
            throw new IllegalStateException(String.format("Data type %s is not compatible with Java String", dataType));
        }
    }

    @Override
    public String get(final int... indices) {
        final ByteBuffer byteBuffer = getByteBufferAt(indices);
        return getNextString(byteBuffer, new byte[dataType.getWidthBytes()]);
    }

    @Override
    protected NdArray<StringNdArray> newInstance(final DataType dataType, final int[] shape, final ByteOrder byteOrder, final int[] strides, final int offset, final Block block) {
        return new StringNdArrayImpl(dataType, shape, byteOrder, strides, offset, block);
    }

    @Override
    protected String getClassName() {
        return "StringNdArray";
    }

    @Override
    public <ARRAY> ARRAY toArray(final ARRAY array) {
        final byte[] buff = new byte[dataType.getWidthBytes()];

        final ArraySetter<String[]> setter = (byteBuffer, arr, index, length) -> {
            for (int i = index; i < length; i++) {
                arr[i] = getNextString(byteBuffer, buff);
            }
        };

        return toArray(array, Boolean.TYPE, setter);
    }

    private String getNextString(final ByteBuffer byteBuffer, final byte[] buff) {
        byteBuffer.get(buff);

        if (dataType.getFamily() == DataTypeFamilyType.ASCII) {
            int length = 0;
            while (length < buff.length) {
                if (buff[length] == 0) {
                    break;
                }
                length++;
            }
            return new String(buff, 0, length, AsdfCharsets.ASCII);
        } else if (dataType.getFamily() == DataTypeFamilyType.UCS4) {
            int length = 0;
            while (length < buff.length) {
                if (buff[length] == 0 && buff[length + 1] == 0 && buff[length + 2] == 0 && buff[length + 3] == 0) {
                    break;
                }
                length += 4;
            }
            if (byteOrder == ByteOrder.BIG_ENDIAN) {
                return new String(buff, 0, length, AsdfCharsets.UTF_32BE);
            } else {
                return new String(buff, 0, length, AsdfCharsets.UTF_32LE);
            }
        } else {
            throw new RuntimeException("Unhandled data type: " + dataType);
        }
    }
}
