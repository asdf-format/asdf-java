package org.asdfformat.asdf.testing;

import java.io.IOException;
import java.io.InputStream;

public enum CoreReferenceFileType implements ReferenceFile {
    NDARRAY_COMPRESSED_ZLIB,
    NDARRAY_FLOAT64_1D_BLOCK_BIG,
    NDARRAY_FLOAT64_1D_BLOCK_LITTLE,
    NDARRAY_FLOAT64_1D_INLINE,
    NDARRAY_STRUCTURED_1D_BLOCK,
    ;

    @Override
    public String getName() {
        return name();
    }

    @Override
    public InputStream openScript() throws IOException {
        return CoreReferenceFileType.class.getResourceAsStream(
                String.format("/reference-file-scripts/%s.py", name().toLowerCase())
        );
    }
}
