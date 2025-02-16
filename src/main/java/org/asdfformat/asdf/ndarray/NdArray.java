package org.asdfformat.asdf.ndarray;


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
    T index(long... indices);

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
     * View of this array that returns boolean
     * elements.
     * @return boolean array view
     */
    BooleanNdArray asBooleanNdArray();

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
}
