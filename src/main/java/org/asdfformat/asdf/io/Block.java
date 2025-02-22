package org.asdfformat.asdf.io;

import java.io.IOException;
import java.nio.ByteBuffer;

public interface Block {
    long getEndPosition();

    ByteBuffer getDataBuffer() throws IOException;
}
