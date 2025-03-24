package org.asdfformat.asdf.ndarray.impl;

import org.asdfformat.asdf.io.LowLevelFormat;
import org.asdfformat.asdf.ndarray.BigIntegerNdArray;
import org.asdfformat.asdf.ndarray.DataType;

import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class BigIntegerNdArrayImpl extends NdArrayBase<BigIntegerNdArray> implements BigIntegerNdArray {
    public BigIntegerNdArrayImpl(final DataType dataType, final int[] shape, final ByteOrder byteOrder, final int[] strides, final int offset, final int source, final LowLevelFormat lowLevelFormat) {
        super(dataType, shape, byteOrder, strides, offset, source, lowLevelFormat);
    }

    @Override
    protected BigIntegerNdArray newInstance(final DataType dataType, final int[] shape, final ByteOrder byteOrder, final int[] strides, final int offset, final int source, final LowLevelFormat lowLevelFormat) {
        return new BigIntegerNdArrayImpl(dataType, shape, byteOrder, strides, offset, source, lowLevelFormat);
    }

    @Override
    protected String getClassName() {
        return "BigIntegerNdArray";
    }

    @Override
    public BigInteger get(final int... indices) {
        final ByteBuffer byteBuffer = getByteBufferAt(indices);

        final byte[] buffer = new byte[dataType.getWidthBytes()];
        if (byteOrder == ByteOrder.BIG_ENDIAN) {
            byteBuffer.get(buffer);
        } else {
            ByteBufferUtils.getReverse(byteBuffer, buffer, 0, buffer.length);
        }

        if (DataType.SIGNED_INTEGRAL_TYPES.contains(dataType)) {
            return new BigInteger(buffer);
        } else if (DataType.UNSIGNED_INTEGRAL_TYPES.contains(dataType)) {
            return new BigInteger(1, buffer);
        } else {
            throw new RuntimeException("Unhandled datatype: " + dataType);
        }
    }

    @Override
    public <ARRAY> ARRAY toArray(final ARRAY array) {
        final BiConsumer<ByteBuffer, byte[]> byteGetter;
        if (byteOrder == ByteOrder.BIG_ENDIAN) {
            byteGetter = ByteBuffer::get;
        } else {
            byteGetter = ByteBufferUtils::getReverse;
        }

        final Function<byte[], BigInteger> valueCreator;
        if (DataType.SIGNED_INTEGRAL_TYPES.contains(dataType)) {
            valueCreator = BigInteger::new;
        } else if (DataType.UNSIGNED_INTEGRAL_TYPES.contains(dataType)) {
            valueCreator = b -> new BigInteger(1, b);
        } else {
            throw new RuntimeException("Unhandled datatype: " + dataType);
        }

        final byte[] buffer = new byte[dataType.getWidthBytes()];

        final ArraySetter<BigInteger[]> setter = (byteBuffer, arr, index, length) -> {
            for (int i = 0; i < length; i++) {
                byteGetter.accept(byteBuffer, buffer);
                arr[index + i] = valueCreator.apply(buffer);
            }
        };

        return toArray(array, BigInteger.class, setter);
    }
}
