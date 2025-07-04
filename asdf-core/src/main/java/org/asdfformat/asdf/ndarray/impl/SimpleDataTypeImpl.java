package org.asdfformat.asdf.ndarray.impl;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.asdfformat.asdf.ndarray.DataType;
import org.asdfformat.asdf.ndarray.DataTypeFamilyType;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@EqualsAndHashCode(of = {"family", "widthBytes"})
@ToString(of = {"family", "widthBytes"})
public class SimpleDataTypeImpl implements DataType {
    @Getter
    private final DataTypeFamilyType family;
    @Getter
    private final int widthBytes;
    private final Set<Type> compatibleTypes;

    @Override
    public boolean isCompatibleWith(final Class<?> type) {
        return compatibleTypes.contains(type);
    }

    @Override
    public List<Field> getFields() {
        return null;
    }
}
