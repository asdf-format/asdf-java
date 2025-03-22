package org.asdfformat.asdf.ndarray;


import java.nio.ByteOrder;

/**
 * Class for accessing ASDF n-dimensional array data.
 */
public interface NdArray<T> {

    /**
     * Get the array datatype, as stored on disk.
     * @return datatype
     */
    DataType getDataType();

    /**
     * Get the array shape.
     * @return shape
     */
    Shape getShape();

    /**
     * Get the byte order of the raw array data.
     * @return byte order
     */
    ByteOrder getByteOrder();

    /**
     * Flag indicating if the array is compressed on disk.
     * @return true if compressed, false otherwise
     */
    boolean isCompressed();

    /**
     * View a portion of the array defined by one or
     * more slices.
     * @param slices 1 or more array slices (maximum one per dimension)
     * @return resulting array view
     */
    T slice(Slice... slices);

    /**
     * View a portion of the array defined by one
     * or more indices.
     * @param indices 1 or more array indices
     * @return resulting array view
     */
    T index(int... indices);

    /**
     * Get array data as an N-dimensional Java array.  If the array
     * argument is sized appropriately, it will be used to return the
     * data.  Otherwise, a new array will be allocated matching its type.
     * @param array destination array / type indicator
     * @return array data
     * @param <ARRAY> N-dimensional Java array type with supported element type
     */
    <ARRAY> ARRAY toArray(ARRAY array);

    /**
     * View of this array that returns BigDecimal
     * elements.
     * @return BigDecimal array view
     */
    BigDecimalNdArray asBigDecimalNdArray();

    /**
     * View of this array that returns BigInteger
     * elements.
     * @return BigInteger array view
     */
    BigIntegerNdArray asBigIntegerNdArray();

    /**
     * View of this array that returns byte
     * elements.
     * @return byte array view
     */
    ByteNdArray asByteNdArray();

    /**
     * View of this array that returns double
     * elements.
     * @return double array view
     */
    DoubleNdArray asDoubleNdArray();

    /**
     * View of this array that returns float
     * elements.
     * @return float array view
     */
    FloatNdArray asFloatNdArray();

    /**
     * View of this array that returns int
     * elements.
     * @return int array view
     */
    IntNdArray asIntNdArray();

    /**
     * View of this array that returns long
     * elements.
     * @return long array view
     */
    LongNdArray asLongNdArray();

    /**
     * View of this array that returns short
     * elements.
     * @return short array view
     */
    ShortNdArray asShortNdArray();

    /**
     * Get the raw array data as a 1-dimensional array of bytes.  The data type, shape, and byte order
     * are needed to properly interpret this array.  This method always returns contiguous array data
     * in row-major order, regardless of the strides configuration of the array on disk.
     *
     * @return raw array bytes
     */
    byte[] toRawArray();
}
