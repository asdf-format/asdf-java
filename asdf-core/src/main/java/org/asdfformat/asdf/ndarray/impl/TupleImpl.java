package org.asdfformat.asdf.ndarray.impl;

import lombok.RequiredArgsConstructor;
import org.asdfformat.asdf.ndarray.DataType;
import org.asdfformat.asdf.ndarray.Tuple;
import org.asdfformat.asdf.ndarray.TupleNdArray;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

@RequiredArgsConstructor
public class TupleImpl implements Tuple {
    private final TupleNdArray singleElementView;

    @Override
    public DataType getDataType() {
        return singleElementView.getDataType();
    }

    @Override
    public List<String> getFieldNames() {
        return singleElementView.getFieldNames();
    }

    @Override
    public BigDecimal getBigDecimal(final String name) {
        return singleElementView.getField(name).asBigDecimalNdArray().get(0);
    }

    @Override
    public BigDecimal getBigDecimal(final int index) {
        return singleElementView.getField(index).asBigDecimalNdArray().get(0);
    }

    @Override
    public BigInteger getBigInteger(final String name) {
        return singleElementView.getField(name).asBigIntegerNdArray().get(0);
    }

    @Override
    public BigInteger getBigInteger(final int index) {
        return singleElementView.getField(index).asBigIntegerNdArray().get(0);
    }

    @Override
    public boolean getBoolean(final String name) {
        return singleElementView.getField(name).asBooleanNdArray().get(0);
    }

    @Override
    public boolean getBoolean(final int index) {
        return singleElementView.getField(index).asBooleanNdArray().get(0);
    }

    @Override
    public byte getByte(final String name) {
        return singleElementView.getField(name).asByteNdArray().get(0);
    }

    @Override
    public byte getByte(final int index) {
        return singleElementView.getField(index).asByteNdArray().get(0);
    }

    @Override
    public double getDouble(final String name) {
        return singleElementView.getField(name).asDoubleNdArray().get(0);
    }

    @Override
    public double getDouble(final int index) {
        return singleElementView.getField(index).asDoubleNdArray().get(0);
    }

    @Override
    public float getFloat(final String name) {
        return singleElementView.getField(name).asFloatNdArray().get(0);
    }

    @Override
    public float getFloat(final int index) {
        return singleElementView.getField(index).asFloatNdArray().get(0);
    }

    @Override
    public int getInt(final String name) {
        return singleElementView.getField(name).asIntNdArray().get(0);
    }

    @Override
    public int getInt(final int index) {
        return singleElementView.getField(index).asIntNdArray().get(0);
    }

    @Override
    public long getLong(final String name) {
        return singleElementView.getField(name).asLongNdArray().get(0);
    }

    @Override
    public long getLong(final int index) {
        return singleElementView.getField(index).asLongNdArray().get(0);
    }

    @Override
    public short getShort(final String name) {
        return singleElementView.getField(name).asShortNdArray().get(0);
    }

    @Override
    public short getShort(final int index) {
        return singleElementView.getField(index).asShortNdArray().get(0);
    }

    @Override
    public String getString(final String name) {
        return singleElementView.getField(name).asStringNdArray().get(0);
    }

    @Override
    public String getString(final int index) {
        return singleElementView.getField(index).asStringNdArray().get(0);
    }

    @Override
    public Tuple getTuple(final String name) {
        return singleElementView.getField(name).asTupleNdArray().get(0);
    }

    @Override
    public Tuple getTuple(final int index) {
        return singleElementView.getField(index).asTupleNdArray().get(0);
    }
}
