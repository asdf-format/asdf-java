package org.asdfformat.asdf.testing;

public enum TestFileType {
    NDARRAY_FLOAT64_1D_BLOCK_BIG,
    NDARRAY_FLOAT64_1D_BLOCK_LITTLE,
    NDARRAY_FLOAT64_1D_INLINE,
    ;

    public String getScriptResourceName() {
        return String.format("/generation/%s.py", name().toLowerCase());
    }
}
