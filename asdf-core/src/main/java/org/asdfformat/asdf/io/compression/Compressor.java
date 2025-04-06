package org.asdfformat.asdf.io.compression;

import java.io.IOException;
import java.nio.ByteBuffer;

public interface Compressor {
    byte[] getIdentifier();

    long decompress(ByteBuffer inputBuffer, ByteBuffer outputBuffer) throws IOException;
}
