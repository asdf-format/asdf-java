package org.asdfformat.asdf.standard;

import org.asdfformat.asdf.metadata.AsdfMetadata;
import org.asdfformat.asdf.node.AsdfNode;

public interface AsdfStandard {
    AsdfMetadata getMetadata();
    AsdfNode getTree();
}
