package org.asdfformat.asdf.testing;

import java.io.IOException;
import java.io.InputStream;

public interface ReferenceFile {
    String getName();
    InputStream openScript() throws IOException;
}
