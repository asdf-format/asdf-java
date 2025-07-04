package org.asdfformat.asdf.ndarray.impl;

import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.asdfformat.asdf.ndarray.DataType;
import org.asdfformat.asdf.ndarray.DataTypeFamilyType;

import java.util.List;

@RequiredArgsConstructor
@EqualsAndHashCode
@ToString
public class StringDataTypeImpl implements DataType {
    private final DataTypeFamilyType family;
    private final int widthBytes;

    @Override
    public DataTypeFamilyType getFamily() {
        return family;
    }

    @Override
    public int getWidthBytes() {
        return widthBytes;
    }

    @Override
    public boolean isCompatibleWith(final Class<?> type) {
        return String.class.equals(type);
    }

    @Override
    public List<Field> getFields() {
        return null;
    }
}
