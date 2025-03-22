package org.asdfformat.asdf.ndarray.impl;

import org.asdfformat.asdf.io.LowLevelFormat;
import org.asdfformat.asdf.ndarray.DataType;
import org.asdfformat.asdf.ndarray.NdArray;

import java.nio.ByteOrder;


public class NdArrayImpl extends NdArrayBase {
    public NdArrayImpl(DataType dataType, int[] shape, ByteOrder byteOrder, int[] strides, int offset, int source, LowLevelFormat lowLevelFormat) {
        super(dataType, shape, byteOrder, strides, offset, source, lowLevelFormat);
    }

    @Override
    protected NdArray<?> newInstance(DataType dataType, int[] shape, ByteOrder byteOrder, int[] strides, int offset, int source, LowLevelFormat lowLevelFormat) {
        return new NdArrayImpl(dataType, shape, byteOrder, strides, offset, source, lowLevelFormat);
    }

    @Override
    protected String getClassName() {
        return "NdArray";
    }

    @Override
    public Object toArray(final Object o) {
        return null;
    }

    //    @Override
//    public <ARRAY> ARRAY toArray(final ARRAY array) {
//        final Class<?> elementType = getElementType(array.getClass());
//
//        if (elementType == BigDecimal.class) {
//            return asBigDecimalNdArray().toArray(array);
//        } else if (elementType == BigInteger.class) {
//            return asBigIntegerNdArray().toArray(array);
//        } else if (elementType == Byte.TYPE) {
//            return asByteNdArray().toArray(array);
//        } else if (elementType == Double.TYPE) {
//            return asDoubleNdArray().toArray(array);
//        } else if (elementType == Float.TYPE) {
//            return asFloatNdArray().toArray(array);
//        } else if (elementType == Integer.TYPE) {
//            return asIntNdArray().toArray(array);
//        } else if (elementType == Long.TYPE) {
//            return asLongNdArray().toArray(array);
//        } else if (elementType == Short.TYPE) {
//            return asShortNdArray().toArray(array);
//        } else {
//            throw new IllegalArgumentException("Unhandled Java array element type: " + elementType.getName());
//        }
//    }

    private Class<?> getElementType(final Class<?> clazz) {
        if (clazz.isArray()) {
            return getElementType(clazz.getComponentType());
        } else {
            return clazz;
        }
    }
}
