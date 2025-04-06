package org.asdfformat.asdf.standard.impl;

import lombok.RequiredArgsConstructor;
import org.asdfformat.asdf.io.LowLevelFormat;
import org.asdfformat.asdf.metadata.AsdfMetadata;
import org.asdfformat.asdf.ndarray.NdArray;
import org.asdfformat.asdf.node.AsdfNode;
import org.asdfformat.asdf.node.impl.NdArrayAsdfNode;
import org.asdfformat.asdf.standard.AsdfStandard;
import org.asdfformat.asdf.util.Version;

import java.util.Set;

@RequiredArgsConstructor
public class AsdfStandardImpl implements AsdfStandard {
    private final Version version;
    private final TreeHandler treeHandler;
    private final NdArrayHandler ndArrayHandler;

    @Override
    public Version getVersion() {
        return version;
    }

    @Override
    public AsdfMetadata getAsdfMetadata(final LowLevelFormat lowLevelFormat, final AsdfNode rawTree) {
        return treeHandler.getAsdfMetadata(lowLevelFormat, rawTree);
    }

    @Override
    public AsdfNode getTree(final AsdfNode rawTree) {
        return treeHandler.getTree(rawTree);
    }

    @Override
    public Set<String> getNdArrayTags() {
        return ndArrayHandler.getNdArrayTags();
    }

    @Override
    public NdArray<?> createNdArray(final LowLevelFormat lowLevelFormat, final NdArrayAsdfNode node) {
        return ndArrayHandler.createNdArray(lowLevelFormat, node);
    }
}
