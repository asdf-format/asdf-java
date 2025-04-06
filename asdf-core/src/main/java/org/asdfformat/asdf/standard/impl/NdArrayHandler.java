package org.asdfformat.asdf.standard.impl;

import org.asdfformat.asdf.io.LowLevelFormat;
import org.asdfformat.asdf.ndarray.NdArray;
import org.asdfformat.asdf.node.impl.NdArrayAsdfNode;

import java.util.Set;

public interface NdArrayHandler {
    Set<String> getNdArrayTags();

    NdArray<?> createNdArray(LowLevelFormat lowLevelFormat, NdArrayAsdfNode node);
}
