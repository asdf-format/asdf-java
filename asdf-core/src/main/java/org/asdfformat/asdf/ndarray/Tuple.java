package org.asdfformat.asdf.ndarray;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

public interface Tuple {
    DataType getDataType();

    List<String> getFieldNames();

    BigDecimal getBigDecimal(String name);

    BigDecimal getBigDecimal(int index);

    BigInteger getBigInteger(String name);

    BigInteger getBigInteger(int index);

    boolean getBoolean(String name);

    boolean getBoolean(int index);

    byte getByte(String name);

    byte getByte(int index);

    double getDouble(String name);

    double getDouble(int index);

    float getFloat(String name);

    float getFloat(int index);

    int getInt(String name);

    int getInt(int index);

    long getLong(String name);

    long getLong(int index);

    short getShort(String name);

    short getShort(int index);

    String getString(String name);

    String getString(int index);

    Tuple getTuple(String name);

    Tuple getTuple(int index);
}
