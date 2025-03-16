package org.asdfformat.asdf.ndarray.impl;

import org.asdfformat.asdf.io.LowLevelFormat;
import org.asdfformat.asdf.ndarray.DataType;
import org.asdfformat.asdf.ndarray.DoubleNdArray;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.DoubleBuffer;

public class DoubleNdArrayImpl extends NdArrayBase<DoubleNdArray> implements DoubleNdArray {
    public DoubleNdArrayImpl(DataType dataType, int[] shape, ByteOrder byteOrder, int[] strides, int offset, int source, LowLevelFormat lowLevelFormat) {
        super(dataType, shape, byteOrder, strides, offset, source, lowLevelFormat);

        if (!dataType.isCompatibleWith(Double.TYPE)) {
            throw new IllegalStateException(String.format("Data type %s is not compatible with Java double", dataType));
        }
    }

    @Override
    protected DoubleNdArray newInstance(DataType dataType, int[] shape, ByteOrder byteOrder, int[] strides, int offset, int source, LowLevelFormat lowLevelFormat) {
        return new DoubleNdArrayImpl(dataType, shape, byteOrder, strides, offset, source, lowLevelFormat);
    }

    @Override
    protected String getClassName() {
        return "DoubleNdArray";
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

        byteBuffer.position(offset);
        byteBuffer.order(byteOrder);
        if (dataType == DataType.FLOAT64) {
            return byteBuffer.getDouble();
        } else if (dataType == DataType.FLOAT32) {
            return byteBuffer.getFloat();
        } else {
            throw new RuntimeException("Unhandled datatype: " + dataType);
        }
    }

    @Override
    public double[] toArray(double[] array) {
        int length = 1;
        for (int i = 0; i < shape.length; i++) {
            length *= shape[i];
        }

        final double[] result = new double[length];

        final ByteBuffer byteBuffer = getByteBuffer();
        if (isCContiguous()) {
            byteBuffer.position(offset);
            byteBuffer.order(byteOrder);
            final DoubleBuffer doubleBuffer = byteBuffer.asDoubleBuffer();
            doubleBuffer.get(result);
            return result;
        } else {
            throw new RuntimeException("Support for non-C-continguous arrays not implemented yet");
//            int[] positions = new int[shape.length];
//            byteBuffer.order(byteOrder);
//            for (int i = 0; i < length; i++) {
//                int elementOffset = offset;
//                for (int j = 0; j < shape.length; j++) {
//                    elementOffset += positions[j] * strides[j];
//                }
//                result[i] = byteBuffer.position(elementOffset).getDouble();
//                positions[0] += 1;
//                for (int dim = 0; dim < shape.length; dim++) {
//                    if (positions[dim] < shape[dim]) {
//                        break;
//                    }
//                    positions[dim + 1] += 1;
//                    positions[dim] = 0;
//                }
//            }
//            return result;
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
