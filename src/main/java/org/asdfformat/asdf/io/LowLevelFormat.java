package org.asdfformat.asdf.io;

public interface LowLevelFormat {
    byte[] getTreeBytes();

    Block getBlock(int index);
}
