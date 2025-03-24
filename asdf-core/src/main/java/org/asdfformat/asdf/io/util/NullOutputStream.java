package org.asdfformat.asdf.io.util;

import java.io.IOException;
import java.io.OutputStream;

public class NullOutputStream extends OutputStream {
    @Override
    public void write(final int b) throws IOException {
        // So long!
    }
}
