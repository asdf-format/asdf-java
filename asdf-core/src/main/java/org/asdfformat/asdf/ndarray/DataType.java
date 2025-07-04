package org.asdfformat.asdf.ndarray;

import java.nio.ByteOrder;
import java.util.List;

/**
 * Element datatype of an n-dimensional array.
 */
public interface DataType {
    DataTypeFamilyType getFamily();

    int getWidthBytes();

    boolean isCompatibleWith(final Class<?> type);

    List<Field> getFields();

    interface Field {
        String getName();
        DataType getDataType();
        ByteOrder getByteOrder();
    }
}
