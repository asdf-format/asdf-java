package org.asdfformat.asdf.ndarray.impl;

import org.asdfformat.asdf.io.LowLevelFormat;
import org.asdfformat.asdf.ndarray.*;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;

public abstract class NdArrayBase<T> implements NdArray<T> {
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
        throw new RuntimeException("Not implemented yet");
    }

    @Override
    public BooleanNdArray asBooleanNdArray() {
        throw new RuntimeException("Not implemented yet");
    }

    @Override
    public ByteNdArray asByteNdArray() {
        throw new RuntimeException("Not implemented yet");
    }

    @Override
    public DoubleNdArray asDoubleNdArray() {
        return new DoubleNdArrayImpl(dataType, shape, byteOrder, strides, offset, source, lowLevelFormat);
    }

    @Override
    public FloatNdArray asFloatNdArray() {
        throw new RuntimeException("Not implemented yet");
    }

    @Override
    public IntNdArray asIntNdArray() {
        throw new RuntimeException("Not implemented yet");
    }

    @Override
    public LongNdArray asLongNdArray() {
        return new LongNdArrayImpl(dataType, shape, byteOrder, strides, offset, source, lowLevelFormat);
    }

    @Override
    public ShortNdArray asShortNdArray() {
        throw new RuntimeException("Not implemented yet");
    }

    @Override
    public ByteOrder getRawByteOrder() {
        return byteOrder;
    }

    @Override
    public byte[] toRawArray() {
        if (isCContiguous()) {
            int length = dataType.getWidthBytes();
            for (int i = 0; i < shape.length; i++) {
                length *= shape[i];
            }
            final byte[] result = new byte[length];
            final ByteBuffer byteBuffer = getByteBuffer();
            byteBuffer.position(offset);
            byteBuffer.get(result);
            return result;
        } else {
            throw new RuntimeException("Not implemented yet");
        }
    }

    protected ByteBuffer getByteBuffer() {
        return lowLevelFormat.getBlock(source).getDataBuffer();
    }

    protected boolean isCContiguous() {
        int nextStrides = dataType.getWidthBytes();
        for (int i = shape.length - 1; i >= 0; i--) {
            if (strides[i] != nextStrides) {
                return false;
            }
            nextStrides *= shape[i];
        }
        return true;
    }
}
