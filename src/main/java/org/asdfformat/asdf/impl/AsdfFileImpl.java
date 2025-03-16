package org.asdfformat.asdf.impl;

import org.asdfformat.asdf.AsdfFile;
import org.asdfformat.asdf.io.util.IOUtils;
import org.asdfformat.asdf.metadata.AsdfMetadata;
import org.asdfformat.asdf.node.AsdfNode;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Path;

public class AsdfFileImpl implements AsdfFile {
    private final Path path;
    private final RandomAccessFile file;
    private final AsdfMetadata metadata;
    private final AsdfNode tree;

    public AsdfFileImpl(final Path path, final RandomAccessFile file, final AsdfMetadata metadata, final AsdfNode tree) {
        this.path = path;
        this.file = file;
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
        IOUtils.closeQuietly(file);
    }

    @Override
    public String toString() {
        return "AsdfFile(path=" + path + ")";
    }
}
