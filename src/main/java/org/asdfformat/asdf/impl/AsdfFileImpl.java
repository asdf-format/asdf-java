package org.asdfformat.asdf.impl;

import java.io.IOException;

import org.asdfformat.asdf.AsdfFile;
import org.asdfformat.asdf.metadata.AsdfMetadata;
import org.asdfformat.asdf.node.AsdfNode;

public class AsdfFileImpl implements AsdfFile {
    private final AsdfMetadata metadata;
    private final AsdfNode tree;

    public AsdfFileImpl(final AsdfMetadata metadata, final AsdfNode tree) {
        this.metadata = metadata;
        this.tree = tree;
    }

    @Override
    public AsdfMetadata getMetadata() {
        return metadata;
    }

    @Override
    public AsdfNode getTree() {
        return tree;
    }

    @Override
    public void close() throws IOException {
        // TODO
    }
}
