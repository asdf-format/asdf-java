package org.asdfformat.asdf.ndarray.impl;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.asdfformat.asdf.ndarray.DataType;
import org.asdfformat.asdf.ndarray.DataTypeFamilyType;
import org.asdfformat.asdf.ndarray.Tuple;

import java.util.List;

@EqualsAndHashCode(of = {"fields"})
@ToString(of = {"fields"})
public class TupleDataTypeImpl implements DataType {
    private final List<Field> fields;
    private final int widthBytes;

    public TupleDataTypeImpl(final List<Field> fields) {
        this.fields = fields;
        this.widthBytes = fields.stream().mapToInt(f -> f.getDataType().getWidthBytes()).sum();
    }

    @Override
    public DataTypeFamilyType getFamily() {
        return DataTypeFamilyType.TUPLE;
    }

    @Override
    public int getWidthBytes() {
        return widthBytes;
    }

    @Override
    public boolean isCompatibleWith(final Class<?> type) {
        return Tuple.class.isAssignableFrom(type);
    }

    @Override
    public List<Field> getFields() {
        return fields;
    }
}
