package org.asdfformat.asdf.ndarray.impl;

import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.asdfformat.asdf.ndarray.DataType;

import java.nio.ByteOrder;

@RequiredArgsConstructor
@EqualsAndHashCode
@ToString
public class DataTypeFieldImpl implements DataType.Field {
    private final String name;
    private final DataType dataType;
    private final ByteOrder byteOrder;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public DataType getDataType() {
        return dataType;
    }

    @Override
    public ByteOrder getByteOrder() {
        return byteOrder;
    }
}
