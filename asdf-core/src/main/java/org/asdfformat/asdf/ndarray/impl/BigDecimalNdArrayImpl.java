package org.asdfformat.asdf.ndarray.impl;

import org.asdfformat.asdf.io.Block;
import org.asdfformat.asdf.ndarray.BigDecimalNdArray;
import org.asdfformat.asdf.ndarray.DataType;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class BigDecimalNdArrayImpl extends NdArrayBase<BigDecimalNdArray> implements BigDecimalNdArray {
    public BigDecimalNdArrayImpl(final DataType dataType, final int[] shape, final ByteOrder byteOrder, final int[] strides, final int offset, final Block block) {
        super(dataType, shape, byteOrder, strides, offset, block);
    }

    @Override
    protected BigDecimalNdArray newInstance(final DataType dataType, final int[] shape, final ByteOrder byteOrder, final int[] strides, final int offset, final Block block) {
        return new BigDecimalNdArrayImpl(dataType, shape, byteOrder, strides, offset, block);
    }

    @Override
    protected String getClassName() {
        return "BigDecimalNdArray";
    }

    @Override
    public BigDecimal get(final int... indices) {
        final ByteBuffer byteBuffer = getByteBufferAt(indices);

        if (DataType.INTEGRAL_TYPES.contains(dataType)) {
            final byte[] buffer = new byte[dataType.getWidthBytes()];
            if (byteOrder == ByteOrder.BIG_ENDIAN) {
                byteBuffer.get(buffer);
            } else {
                ByteBufferUtils.getReverse(byteBuffer, buffer, 0, buffer.length);
            }

            if (DataType.SIGNED_INTEGRAL_TYPES.contains(dataType)) {
                return new BigDecimal(new BigInteger(buffer));
            } else if (DataType.UNSIGNED_INTEGRAL_TYPES.contains(dataType)) {
                return new BigDecimal(new BigInteger(1, buffer));
            } else {
                throw new RuntimeException("Unhandled datatype: " + dataType);
            }
        } else if (dataType == DataType.FLOAT32) {
            return BigDecimal.valueOf(byteBuffer.getFloat());
        } else if (dataType == DataType.FLOAT64) {
            return BigDecimal.valueOf(byteBuffer.getDouble());
        } else {
            throw new RuntimeException("Unhandled datatype: " + dataType);
        }
    }

    @Override
    public <ARRAY> ARRAY toArray(final ARRAY array) {
        final ArraySetter<BigDecimal[]> setter;

        if (DataType.INTEGRAL_TYPES.contains(dataType)) {
            final BiConsumer<ByteBuffer, byte[]> byteGetter;
            if (byteOrder == ByteOrder.BIG_ENDIAN) {
                byteGetter = ByteBuffer::get;
            } else {
                byteGetter = ByteBufferUtils::getReverse;
            }

            final Function<byte[], BigDecimal> valueCreator;
            if (DataType.SIGNED_INTEGRAL_TYPES.contains(dataType)) {
                valueCreator = b -> new BigDecimal(new BigInteger(b));
            } else if (DataType.UNSIGNED_INTEGRAL_TYPES.contains(dataType)) {
                valueCreator = b -> new BigDecimal(new BigInteger(1, b));
            } else {
                throw new RuntimeException("Unhandled datatype: " + dataType);
            }

            final byte[] buffer = new byte[dataType.getWidthBytes()];

            setter = (byteBuffer, arr, index, length) -> {
                for (int i = 0; i < length; i++) {
                    byteGetter.accept(byteBuffer, buffer);
                    arr[index + i] = valueCreator.apply(buffer);
                }
            };
        } else if (dataType == DataType.FLOAT32) {
            setter = (byteBuffer, arr, index, length) -> {
                for (int i = 0; i < length; i++) {
                    arr[index + i] = BigDecimal.valueOf(byteBuffer.getFloat());
                }
            };
        } else if (dataType == DataType.FLOAT64) {
            setter = (byteBuffer, arr, index, length) -> {
                for (int i = 0; i < length; i++) {
                    arr[index + i] = BigDecimal.valueOf(byteBuffer.getDouble());
                }
            };
        } else {
            throw new RuntimeException("Unhandled datatype: " + dataType);
        }

        return toArray(array, BigDecimal.class, setter);
    }
}
