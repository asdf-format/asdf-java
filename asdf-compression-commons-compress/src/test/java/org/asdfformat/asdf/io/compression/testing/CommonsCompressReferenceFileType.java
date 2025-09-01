package org.asdfformat.asdf.io.compression.testing;

import org.asdfformat.asdf.testing.ReferenceFile;

import java.io.IOException;
import java.io.InputStream;

public enum CommonsCompressReferenceFileType implements ReferenceFile {
    NDARRAY_COMPRESSED_LZ4,
    ;

    @Override
    public String getName() {
        return name();
    }

    @Override
    public InputStream openScript() throws IOException {
        return CommonsCompressReferenceFileType.class.getResourceAsStream(
                String.format("/test-file-scripts/%s.py", name().toLowerCase())
        );
    }
}
