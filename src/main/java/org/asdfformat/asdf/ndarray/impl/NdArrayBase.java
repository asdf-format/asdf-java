package org.asdfformat.asdf.ndarray.impl;

import org.asdfformat.asdf.io.LowLevelFormat;
import org.asdfformat.asdf.ndarray.BigDecimalNdArray;
import org.asdfformat.asdf.ndarray.BigIntegerNdArray;
import org.asdfformat.asdf.ndarray.ByteNdArray;
import org.asdfformat.asdf.ndarray.DataType;
import org.asdfformat.asdf.ndarray.DoubleNdArray;
import org.asdfformat.asdf.ndarray.FloatNdArray;
import org.asdfformat.asdf.ndarray.IntNdArray;
import org.asdfformat.asdf.ndarray.LongNdArray;
import org.asdfformat.asdf.ndarray.NdArray;
import org.asdfformat.asdf.ndarray.Shape;
import org.asdfformat.asdf.ndarray.ShortNdArray;
import org.asdfformat.asdf.ndarray.Slice;
import org.asdfformat.asdf.ndarray.Slices;

import java.lang.reflect.Array;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class NdArrayBase<T> implements NdArray<T> {
    protected final DataType dataType;
    protected final int[] shape;
    protected final ByteOrder byteOrder;
    protected final int[] strides;
    protected final int offset;
    protected final int source;
    protected final LowLevelFormat lowLevelFormat;
    protected final boolean[] cContiguous;

    public NdArrayBase(DataType dataType, int[] shape, ByteOrder byteOrder, int[] strides, int offset, int source, LowLevelFormat lowLevelFormat) {
        this.dataType = dataType;
        this.shape = shape;
        this.byteOrder = byteOrder;
        this.strides = strides;
        this.offset = offset;
        this.source = source;
        this.lowLevelFormat = lowLevelFormat;

        this.cContiguous = getCContiguous();
    }

    protected abstract T newInstance(
            DataType dataType, int[] shape, ByteOrder byteOrder, int[] strides, int offset, int source, LowLevelFormat lowLevelFormat
    );

    protected abstract String getClassName();

    @Override
    public DataType getDataType() {
        return dataType;
    }

    @Override
    public Shape getShape() {
        return new ShapeImpl(shape);
    }

    @Override
    public ByteOrder getByteOrder() {
        return byteOrder;
    }
    
    @Override
    public boolean isCompressed() {
        return lowLevelFormat.getBlock(source).isCompressed();
    }

    @Override
    public T slice(final Slice... slices) {
        if (slices.length > shape.length) {
            throw new IllegalArgumentException(String.format("Too many slices (%d) for array of rank %d", slices.length, shape.length));
        }

        final List<Slice> sliceList = Arrays.asList(slices);
        while (sliceList.size() < shape.length) {
            sliceList.add(Slices.all());
        }

        final int newOffset = sliceList.get(sliceList.size() - 1).computeNewOffset(offset, strides[strides.length - 1]);
        final List<Integer> newShape = new ArrayList<>(shape.length);
        final List<Integer> newStrides = new ArrayList<>(shape.length);

        for (int i = 0; i < shape.length; i++) {
            final int newLength = sliceList.get(i).computeNewLength(shape[i]);
            if (newLength > 0) {
                newShape.add(newLength);
                newStrides.add(sliceList.get(i).computeNewStride(strides[i]));
            }
        }

        if (newShape.isEmpty()) {
            throw new IllegalArgumentException("Cannot slice array down to a scalar");
        }

        return newInstance(
                dataType,
                newShape.stream().mapToInt(i -> i).toArray(),
                byteOrder,
                newStrides.stream().mapToInt(i -> i).toArray(),
                newOffset,
                source,
                lowLevelFormat
        );
    }

    @Override
    public T index(final int... indices) {
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
        return new BigDecimalNdArrayImpl(dataType, shape, byteOrder, strides, offset, source, lowLevelFormat);
    }

    @Override
    public BigIntegerNdArray asBigIntegerNdArray() {
        return new BigIntegerNdArrayImpl(dataType, shape, byteOrder, strides, offset, source, lowLevelFormat);
    }

    @Override
    public ByteNdArray asByteNdArray() {
        return new ByteNdArrayImpl(dataType, shape, byteOrder, strides, offset, source, lowLevelFormat);
    }

    @Override
    public DoubleNdArray asDoubleNdArray() {
        return new DoubleNdArrayImpl(dataType, shape, byteOrder, strides, offset, source, lowLevelFormat);
    }

    @Override
    public FloatNdArray asFloatNdArray() {
        return new FloatNdArrayImpl(dataType, shape, byteOrder, strides, offset, source, lowLevelFormat);
    }

    @Override
    public IntNdArray asIntNdArray() {
        return new IntNdArrayImpl(dataType, shape, byteOrder, strides, offset, source, lowLevelFormat);
    }

    @Override
    public LongNdArray asLongNdArray() {
        return new LongNdArrayImpl(dataType, shape, byteOrder, strides, offset, source, lowLevelFormat);
    }

    @Override
    public ShortNdArray asShortNdArray() {
        return new ShortNdArrayImpl(dataType, shape, byteOrder, strides, offset, source, lowLevelFormat);
    }

    @Override
    public byte[] toRawArray() {
        if (cContiguous[0]) {
            int totalLength = dataType.getWidthBytes();
            for (final int length : shape) {
                totalLength *= length;
            }
            final byte[] result = new byte[totalLength];
            final ByteBuffer byteBuffer = getByteBuffer();
            byteBuffer.position(offset);
            byteBuffer.get(result);
            return result;
        } else {
            throw new RuntimeException("Not implemented yet");
        }
    }

    @Override
    public String toString() {
        return String.format(
                "%s(shape=%s, datatype=%s)",
                getClassName(),
                getShape(),
                getDataType()
        );
    }

    protected ByteBuffer getByteBufferAt(final int... indices) {
        if (indices.length != shape.length) {
            throw new IllegalArgumentException("Number of indices must match array rank");
        }

        final ByteBuffer byteBuffer = getByteBuffer();
        int offset = this.offset;
        for (int i = 0; i < indices.length; i++) {
            if (indices[i] < 0 || indices[i] >= shape[i]) {
                throw new IllegalArgumentException("Index out of range");
            }
            offset += indices[i] * strides[i];
        }

        byteBuffer.position(offset);
        byteBuffer.order(byteOrder);

        return byteBuffer;
    }

    @FunctionalInterface
    protected interface ArraySetter<ONE_DIM_ARRAY> {
        void accept(ByteBuffer byteBuffer, ONE_DIM_ARRAY dest, int index, int length);
    }

    @SuppressWarnings("unchecked")
    protected <ARRAY, ONE_DIM_ARRAY> ARRAY toArray(final ARRAY array, final Class<?> elementType, final ArraySetter<ONE_DIM_ARRAY> setter) {
        final int[] arrayShape = getArrayShape(array, elementType);

        if (arrayShape.length < 1 || arrayShape.length > shape.length) {
            throw new IllegalArgumentException(
                    String.format(
                            "Cannot convert array of rank %d to Java array of rank %d",
                            shape.length,
                            arrayShape.length
                    )
            );
        }

        final ARRAY dest;
        final int[] destShape = getCompatibleShape(arrayShape.length);
        if (Arrays.equals(arrayShape, destShape)) {
            dest = array;
        } else {
            dest = (ARRAY) Array.newInstance(elementType, destShape);
        }

        copyToArray(
                0,
                offset,
                getTotalLength(),
                getByteBuffer(),
                destShape,
                0,
                dest,
                setter
        );

        return dest;
    }

    private ByteBuffer getByteBuffer() {
        return lowLevelFormat.getBlock(source).getDataBuffer();
    }

    @SuppressWarnings("unchecked")
    private <ARRAY, ONE_DIM_ARRAY> void copyToArray(
            final int dim,
            final int offset,
            final int remainingLength,
            final ByteBuffer byteBuffer,
            final int[] destShape,
            final int destIndex,
            final ARRAY dest,
            final ArraySetter<ONE_DIM_ARRAY> setter
    ) {
        if (dim >= destShape.length - 1) {
            if (cContiguous[dim]) {
                byteBuffer.position(offset);
                setter.accept(byteBuffer, (ONE_DIM_ARRAY) dest, destIndex, remainingLength);
            } else if (dim < shape.length - 1) {
                final int newRemainingLength = remainingLength / shape[dim];
                for (int i = 0; i < shape[dim]; i++) {
                    copyToArray(
                            dim,
                            offset + strides[dim] + i,
                            newRemainingLength,
                            byteBuffer,
                            destShape,
                            destIndex + i * newRemainingLength,
                            dest,
                            setter
                    );
                }
            } else {
                for (int i = 0; i < shape[dim]; i++) {
                    byteBuffer.position(offset + strides[dim] * i);
                    setter.accept(byteBuffer, (ONE_DIM_ARRAY) dest, destIndex + i, 1);
                }
            }
        } else {
            final int newRemainingLength = remainingLength / shape[dim];
            for (int i = 0; i < shape[dim]; i++) {
                copyToArray(
                        dim + 1,
                        offset + strides[dim] * i,
                        newRemainingLength,
                        byteBuffer,
                        destShape,
                        0,
                        dest,
                        setter
                );
            }
        }
    }

    private <ARRAY> int[] getArrayShape(final ARRAY array, final Class<?> elementType) {
        if (!array.getClass().isArray()) {
            throw new IllegalArgumentException("Expected array type");
        }

        final List<Integer> shape = new ArrayList<>();

        Object currentArray = array;
        Class<?> currentClass = array.getClass();
        while (currentClass.isArray()) {
            if (currentArray != null) {
                final int currentLength = Array.getLength(currentArray);
                shape.add(currentLength);
                if (currentLength > 0) {
                    currentArray = Array.get(currentArray, 0);
                } else {
                    currentArray = null;
                }
            } else {
                shape.add(0);
            }
            currentClass = currentClass.getComponentType();
        }

        if (!elementType.equals(currentClass)) {
            throw new IllegalArgumentException("Expected array of " + elementType.getName());
        }

        return shape.stream().mapToInt(i -> i).toArray();
    }

    private int[] getCompatibleShape(final int rank) {
        final int[] result = new int[rank];

        for (int dim = 0; dim < rank - 1; dim++) {
            result[dim] = shape[dim];
        }

        int lastDimLength = 1;
        for (int dim = rank - 1; dim < shape.length; dim++) {
            lastDimLength *= shape[dim];
        }

        result[result.length - 1] = lastDimLength;

        return result;
    }

    private int getTotalLength() {
        int totalLength = 1;
        for (final int length : shape) {
             totalLength *= length;
        }
        return totalLength;
    }

    private boolean[] getCContiguous() {
        final boolean[] result = new boolean[shape.length];
        Arrays.fill(result, false);

        int nextStrides = dataType.getWidthBytes();
        for (int i = shape.length - 1; i >= 0; i--) {
            if (strides[i] != nextStrides) {
                return result;
            }
            result[i] = true;
            nextStrides *= shape[i];
        }

        return result;
    }
}
