package org.asdfformat.asdf.io;

import java.nio.ByteBuffer;

public interface Block {
    long getEndPosition();

    ByteBuffer getDataBuffer();

    boolean isCompressed();
}
