package org.asdfformat.asdf.testing;

public enum TestFileType {
    NDARRAY_COMPRESSED_ZLIB,
    NDARRAY_FLOAT64_1D_BLOCK_BIG,
    NDARRAY_FLOAT64_1D_BLOCK_LITTLE,
    NDARRAY_FLOAT64_1D_INLINE,
    NDARRAY_STRUCTURED_1D_BLOCK,
    ;

    public String getScriptResourceName() {
        return String.format("/generation/scripts/%s.py", name().toLowerCase());
    }
}
