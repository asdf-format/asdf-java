package org.asdfformat.asdf.io;

import org.asdfformat.asdf.util.Version;

public interface LowLevelFormat {
    Version getAsdfStandardVersion();

    byte[] getTreeBytes();

    Block getBlock(int index);
}
