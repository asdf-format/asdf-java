package org.asdfformat.asdf.standard.impl;

import org.asdfformat.asdf.io.LowLevelFormat;
import org.asdfformat.asdf.metadata.AsdfMetadata;
import org.asdfformat.asdf.node.AsdfNode;

public interface TreeHandler {
    AsdfMetadata getAsdfMetadata(LowLevelFormat lowLevelFormat, AsdfNode rawTree);

    AsdfNode getTree(AsdfNode rawTree);
}


