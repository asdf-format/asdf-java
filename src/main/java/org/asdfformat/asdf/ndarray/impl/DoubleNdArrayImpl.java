package org.asdfformat.asdf.ndarray.impl;

import org.asdfformat.asdf.io.LowLevelFormat;
import org.asdfformat.asdf.ndarray.DataType;
import org.asdfformat.asdf.ndarray.DoubleNdArray;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class DoubleNdArrayImpl extends NdArrayBase<DoubleNdArray> implements DoubleNdArray {
    public DoubleNdArrayImpl(DataType dataType, int[] shape, ByteOrder byteOrder, int[] strides, int offset, int source, LowLevelFormat lowLevelFormat) {
        super(dataType, shape, byteOrder, strides, offset, source, lowLevelFormat);
    }

    @Override
    protected DoubleNdArray newInstance(DataType dataType, int[] shape, ByteOrder byteOrder, int[] strides, int offset, int source, LowLevelFormat lowLevelFormat) {
        return new DoubleNdArrayImpl(dataType, shape, byteOrder, strides, offset, source, lowLevelFormat);
    }

    @Override
    public double get(int... indices) {
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

        return byteBuffer.position(offset).order(byteOrder).getDouble();
    }

    @Override
    public double[] toArray(double[] array) {
        int arrayLength = 1;
        for (int i = 0; i < shape.length; i++) {
            arrayLength *= shape[i];
        }

        final ByteBuffer byteBuffer = getByteBuffer();
        if (isCContiguous()) {
            return byteBuffer
                    .position(offset)
                    .limit(arrayLength * dataType.getWidthBytes())
                    .order(byteOrder)
                    .asDoubleBuffer()
                    .array();
        } else {
            final double[] result = new double[arrayLength];
            int[] positions = new int[shape.length];
            byteBuffer.order(byteOrder);
            for (int i = 0; i < arrayLength; i++) {
                int elementOffset = offset;
                for (int j = 0; j < shape.length; j++) {
                    elementOffset += positions[j] * strides[j];
                }
                result[i] = byteBuffer.position(elementOffset).getDouble();
                positions[0] += 1;
                for (int dim = 0; dim < shape.length; dim++) {
                    if (positions[dim] < shape[dim]) {
                        break;
                    }
                    positions[dim + 1] += 1;
                    positions[dim] = 0;
                }
            }
            return result;
        }
    }

    @Override
    public double[][] toArray(double[][] array) {
        if (shape.length != 2) {
            throw new IllegalStateException(String.format("Array rank %d not compatible with a 2D Java array", shape.length));
        }

        final double[][] result = new double[shape[0]][];

        for (int i = 0; i < shape[0]; i++) {
            result[i] = index(i).toArray(new double[0]);
        }

        return result;
    }

    @Override
    public double[][][] toArray(double[][][] array) {
        if (shape.length != 3) {
            throw new IllegalStateException(String.format("Array rank %d not compatible with a 3D Java array", shape.length));
        }

        final double[][][] result = new double[shape[0]][shape[1]][];

        for (int i = 0; i < shape[0]; i++) {
            for (int j = 0; j < shape[1]; j++) {
                result[i][j] = index(i, j).toArray(new double[0]);
            }
        }

        return result;
    }
}
