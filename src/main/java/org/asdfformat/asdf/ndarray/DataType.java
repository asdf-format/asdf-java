package org.asdfformat.asdf.ndarray;

/**
 * Element datatype of an n-dimensional array.
 */
public enum DataType {
    INT8(1),
    INT16(2),
    INT32(4),
    INT64(8),
    UINT8(1),
    UINT16(2),
    UINT32(4),
    UINT64(8),
    FLOAT32(4),
    FLOAT64(8),
    COMPLEX64(8),
    COMPLEX128(16),
    BOOL8(1),
    ;

    private final int widthBytes;

    DataType(int widthBytes) {
        this.widthBytes = widthBytes;
    }

    public int getWidthBytes() {
        return widthBytes;
    }
}
