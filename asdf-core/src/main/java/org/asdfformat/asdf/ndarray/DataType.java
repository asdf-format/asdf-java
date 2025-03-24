package org.asdfformat.asdf.ndarray;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;

/**
 * Element datatype of an n-dimensional array.
 */
@RequiredArgsConstructor
public enum DataType {
    INT8(1, new HashSet<>(Arrays.asList(Byte.TYPE, Short.TYPE, Integer.TYPE, Long.TYPE, BigInteger.class))),
    INT16(2, new HashSet<>(Arrays.asList(Short.TYPE, Integer.TYPE, Long.TYPE, BigInteger.class))),
    INT32(4, new HashSet<>(Arrays.asList(Integer.TYPE, Long.TYPE, BigInteger.class))),
    INT64(8, new HashSet<>(Arrays.asList(Long.TYPE, BigInteger.class))),
    UINT8(1, new HashSet<>(Arrays.asList(Byte.TYPE, Short.TYPE, Integer.TYPE, Long.TYPE, BigInteger.class))),
    UINT16(2, new HashSet<>(Arrays.asList(Short.TYPE, Integer.TYPE, Long.TYPE, BigInteger.class))),
    UINT32(4, new HashSet<>(Arrays.asList(Integer.TYPE, Long.TYPE, BigInteger.class))),
    UINT64(8, new HashSet<>(Arrays.asList(Long.TYPE, BigInteger.class))),
    FLOAT32(4, new HashSet<>(Arrays.asList(Float.TYPE, Double.TYPE, BigDecimal.class))),
    FLOAT64(8, new HashSet<>(Arrays.asList(Double.TYPE, BigDecimal.class))),
    COMPLEX64(8, Collections.emptySet()),
    COMPLEX128(16, Collections.emptySet()),
    BOOL8(1, new HashSet<>(Arrays.asList(Boolean.TYPE))),
    ;

    public static final Set<DataType> SIGNED_INTEGRAL_TYPES = EnumSet.of(
            DataType.INT8,
            DataType.INT16,
            DataType.INT32,
            DataType.INT64
    );

    public static final Set<DataType> UNSIGNED_INTEGRAL_TYPES = EnumSet.of(
            DataType.UINT8,
            DataType.UINT16,
            DataType.UINT32,
            DataType.UINT64
    );

    public static final Set<DataType> INTEGRAL_TYPES = EnumSet.noneOf(DataType.class);
    static {
        INTEGRAL_TYPES.addAll(SIGNED_INTEGRAL_TYPES);
        INTEGRAL_TYPES.addAll(UNSIGNED_INTEGRAL_TYPES);
    }

    public static DataType fromString(final String value) {
        return DataType.valueOf(value.toUpperCase());
    }

    @Getter
    private final int widthBytes;
    private final Set<Type> compatibleTypes;

    public boolean isCompatibleWith(final Class<?> type) {
        return compatibleTypes.contains(type);
    }
}
