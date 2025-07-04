package org.asdfformat.asdf.ndarray;

import org.asdfformat.asdf.ndarray.impl.SimpleDataTypeImpl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class DataTypes {
    public static final DataType INT8 = new SimpleDataTypeImpl(
            DataTypeFamilyType.INT,
            1,
            new HashSet<>(Arrays.asList(Byte.TYPE, Short.TYPE, Integer.TYPE, Long.TYPE, BigInteger.class))
    );

    public static final DataType INT16 = new SimpleDataTypeImpl(
            DataTypeFamilyType.INT,
            2,
            new HashSet<>(Arrays.asList(Short.TYPE, Integer.TYPE, Long.TYPE, BigInteger.class))
    );

    public static final DataType INT32 = new SimpleDataTypeImpl(
            DataTypeFamilyType.INT,
            4,
            new HashSet<>(Arrays.asList(Integer.TYPE, Long.TYPE, BigInteger.class))
    );

    public static final DataType INT64 = new SimpleDataTypeImpl(
            DataTypeFamilyType.INT,
            8,
            new HashSet<>(Arrays.asList(Long.TYPE, BigInteger.class))
    );

    public static final DataType UINT8 = new SimpleDataTypeImpl(
            DataTypeFamilyType.UINT,
            1,
            new HashSet<>(Arrays.asList(Byte.TYPE, Short.TYPE, Integer.TYPE, Long.TYPE, BigInteger.class))
    );

    public static final DataType UINT16 = new SimpleDataTypeImpl(
            DataTypeFamilyType.UINT,
            2,
            new HashSet<>(Arrays.asList(Short.TYPE, Integer.TYPE, Long.TYPE, BigInteger.class))
    );

    public static final DataType UINT32 = new SimpleDataTypeImpl(
            DataTypeFamilyType.UINT,
            4,
            new HashSet<>(Arrays.asList(Integer.TYPE, Long.TYPE, BigInteger.class))
    );

    public static final DataType UINT64 = new SimpleDataTypeImpl(
            DataTypeFamilyType.UINT,
            8,
            new HashSet<>(Arrays.asList(Long.TYPE, BigInteger.class))
    );

    public static final DataType FLOAT32 = new SimpleDataTypeImpl(
            DataTypeFamilyType.FLOAT,
            4,
            new HashSet<>(Arrays.asList(Float.TYPE, Double.TYPE, BigDecimal.class))
    );

    public static final DataType FLOAT64 = new SimpleDataTypeImpl(
            DataTypeFamilyType.FLOAT,
            8,
            new HashSet<>(Arrays.asList(Double.TYPE, BigDecimal.class))
    );

    public static final DataType COMPLEX64 = new SimpleDataTypeImpl(
            DataTypeFamilyType.COMPLEX,
            8,
            Collections.emptySet()
    );

    public static final DataType COMPLEX128 = new SimpleDataTypeImpl(
            DataTypeFamilyType.COMPLEX,
            16,
            Collections.emptySet()
    );

    public static final DataType BOOL8 = new SimpleDataTypeImpl(
            DataTypeFamilyType.BOOL,
            1,
            new HashSet<>(Arrays.asList(Boolean.TYPE))
    );

    ;

    public static final Set<DataType> SIGNED_INTEGRAL_TYPES = new HashSet<>(Arrays.asList(
            INT8,
            INT16,
            INT32,
            INT64
    ));

    public static final Set<DataType> UNSIGNED_INTEGRAL_TYPES = new HashSet<>(Arrays.asList(
            UINT8,
            UINT16,
            UINT32,
            UINT64
    ));

    public static final Set<DataType> INTEGRAL_TYPES = new HashSet<>();
    static {
        INTEGRAL_TYPES.addAll(SIGNED_INTEGRAL_TYPES);
        INTEGRAL_TYPES.addAll(UNSIGNED_INTEGRAL_TYPES);
    }
}
