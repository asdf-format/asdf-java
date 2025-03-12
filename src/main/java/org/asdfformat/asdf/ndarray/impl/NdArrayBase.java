package org.asdfformat.asdf.ndarray.impl;

import org.asdfformat.asdf.io.LowLevelFormat;
import org.asdfformat.asdf.ndarray.*;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.Set;

public abstract class NdArrayBase<T> implements NdArray<T> {
    private static final Set<DataType> FLOATING_POINT_DATA_TYPES = EnumSet.of(
            DataType.FLOAT32,
            DataType.FLOAT64
    );
    private static final Set<DataType> INTEGRAL_DATA_TYPES = EnumSet.of(
            DataType.INT8,
            DataType.INT16,
            DataType.INT32,
            DataType.INT64,
            DataType.UINT8,
            DataType.UINT16,
            DataType.UINT32,
            DataType.UINT64
    );

    protected final DataType dataType;
    protected final int[] shape;
    protected final ByteOrder byteOrder;
    protected final int[] strides;
    protected final int offset;
    protected final int source;
    protected final LowLevelFormat lowLevelFormat;

    public NdArrayBase(DataType dataType, int[] shape, ByteOrder byteOrder, int[] strides, int offset, int source, LowLevelFormat lowLevelFormat) {
        this.dataType = dataType;
        this.shape = shape;
        this.byteOrder = byteOrder;
        this.strides = strides;
        this.offset = offset;
        this.source = source;
        this.lowLevelFormat = lowLevelFormat;
    }

    protected abstract T newInstance(
            DataType dataType, int[] shape, ByteOrder byteOrder, int[] strides, int offset, int source, LowLevelFormat lowLevelFormat
    );

    @Override
    public DataType getDataType() {
        return dataType;
    }

    @Override
    public Shape getShape() {
        return new ShapeImpl(shape);
    }

    @Override
    public boolean isCompressed() {
        return lowLevelFormat.getBlock(source).isCompressed();
    }

    @Override
    public T slice(Slice... slices) {
        throw new RuntimeException("Not implemented yet");
    }

    @Override
    public T index(int... indices) {
        if (indices.length >= shape.length - 1) {
            throw new IllegalArgumentException(String.format("Too many indices (%d) for array of rank %d", indices.length, shape.length));
        }

        final int[] newShape = Arrays.copyOfRange(shape, indices.length, shape.length);
        final int[] newStrides = Arrays.copyOfRange(strides, indices.length, strides.length);

        int newOffset = offset;
        for (int i = 0; i < indices.length; i++) {
            newOffset += strides[i] * indices[i];
        }

        return newInstance(dataType, newShape, byteOrder, newStrides, newOffset, source, lowLevelFormat);
    }

    @Override
    public BigDecimalNdArray asBigDecimalNdArray() {
        throw new RuntimeException("Not implemented yet");
    }

    @Override
    public BigIntegerNdArray asBigIntegerNdArray() {
        if (!INTEGRAL_DATA_TYPES.contains(dataType)) {
            throw new IllegalStateException(String.format("Cannot represent datatype %s as BigInteger", dataType));
        }
        throw new RuntimeException("Not implemented yet");
    }

    @Override
    public BooleanNdArray asBooleanNdArray() {
        if (dataType != DataType.BOOL8) {
            throw new IllegalStateException(String.format("Cannot represent datatype %s as boolean", dataType));
        }
        throw new RuntimeException("Not implemented yet");
    }

    @Override
    public ByteNdArray asByteNdArray() {
        if (!(INTEGRAL_DATA_TYPES.contains(dataType) && dataType.getWidthBytes() <= 1)) {
            throw new IllegalStateException(String.format("Cannot represent datatype %s as byte", dataType));
        }
        throw new RuntimeException("Not implemented yet");
    }

    @Override
    public DoubleNdArray asDoubleNdArray() {
        if (!(FLOATING_POINT_DATA_TYPES.contains(dataType))) {
            throw new IllegalStateException(String.format("Cannot represent datatype %s as double", dataType));
        }
        return new DoubleNdArrayImpl(dataType, shape, byteOrder, strides, offset, source, lowLevelFormat);
    }

    @Override
    public FloatNdArray asFloatNdArray() {
        if (dataType != DataType.FLOAT32) {
            throw new IllegalStateException(String.format("Cannot represent datatype %s as float", dataType));
        }
        throw new RuntimeException("Not implemented yet");
    }

    @Override
    public IntNdArray asIntNdArray() {
        if (!(INTEGRAL_DATA_TYPES.contains(dataType) && dataType.getWidthBytes() <= 4)) {
            throw new IllegalStateException(String.format("Cannot represent datatype %s as int", dataType));
        }
        throw new RuntimeException("Not implemented yet");
    }

    @Override
    public LongNdArray asLongNdArray() {
        if (!(INTEGRAL_DATA_TYPES.contains(dataType))) {
            throw new IllegalStateException(String.format("Cannot represent datatype %s as long", dataType));
        }
        throw new RuntimeException("Not implemented yet");
    }

    @Override
    public ShortNdArray asShortNdArray() {
        if (!(INTEGRAL_DATA_TYPES.contains(dataType) && dataType.getWidthBytes() <= 2)) {
            throw new IllegalStateException(String.format("Cannot represent datatype %s as short", dataType));
        }
    }

    @Override
    public ByteOrder getRawByteOrder() {
        return byteOrder;
    }

    @Override
    public byte[] toRawArray() {
        if (isCContiguous()) {
            int limit = dataType.getWidthBytes();
            for (int i = 0; i < shape.length; i++) {
                limit *= shape[i];
            }
            return getByteBuffer().position(offset).limit(limit).array();
        } else {
            throw new RuntimeException("Not implemented yet");
        }
    }

    protected ByteBuffer getByteBuffer() {
        return lowLevelFormat.getBlock(source).getDataBuffer();
    }

    protected boolean isCContiguous() {
        int previousLength = 1;
        for (int i = 0; i < shape.length; i++) {
            if (strides[i] != previousLength * dataType.getWidthBytes()) {
                return false;
            }
            previousLength *= shape.length * dataType.getWidthBytes();
        }
        return true;
    }
}
