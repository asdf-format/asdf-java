package org.asdfformat.asdf.standard.impl;

import org.asdfformat.asdf.io.LowLevelFormat;
import org.asdfformat.asdf.metadata.AsdfMetadata;
import org.asdfformat.asdf.metadata.Extension;
import org.asdfformat.asdf.metadata.HistoryEntry;
import org.asdfformat.asdf.metadata.Software;
import org.asdfformat.asdf.metadata.impl.AsdfMetadataImpl;
import org.asdfformat.asdf.metadata.impl.Extension100;
import org.asdfformat.asdf.metadata.impl.HistoryEntry100;
import org.asdfformat.asdf.metadata.impl.Software100;
import org.asdfformat.asdf.node.AsdfNode;
import org.asdfformat.asdf.node.impl.MappingAsdfNode;
import org.asdfformat.asdf.node.impl.StringAsdfNode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class TreeHandler_1_1_0 implements TreeHandler {
    private final String ASDF_TAG = "tag:stsci.edu:asdf/core/asdf-1.1.0";

    @Override
    public AsdfMetadata getAsdfMetadata(final LowLevelFormat lowLevelFormat, final AsdfNode rawTree) {
        if (!rawTree.isMapping() || !rawTree.getTag().equals(ASDF_TAG)) {
            throw new IllegalStateException("Corrupted ASDF file: Top-level node must be a mapping with tag " + ASDF_TAG);
        }

        final MappingAsdfNode rawMappingTree = (MappingAsdfNode) rawTree;
        final Map<AsdfNode, AsdfNode> value = rawMappingTree.getValue();

        final AsdfNode asdfLibraryNode = value.get(StringAsdfNode.of("asdf_library"));
        final AsdfNode historyNode = value.get(StringAsdfNode.of("history"));
        final AsdfNode extensionsNode = Optional.ofNullable(historyNode)
                .flatMap(n ->n.containsKey("extension") ? Optional.of(n.get("extensions")) : Optional.empty())
                .orElse(null);
        final AsdfNode entriesNode = Optional.ofNullable(historyNode)
                .flatMap(n -> n.containsKey("entries") ? Optional.of(n.get("entries")) : Optional.empty())
                .orElse(null);

        final Software asdfLibrary = Optional.ofNullable(asdfLibraryNode)
                .map(Software100::new)
                .orElse(null);

        final List<Extension> extensions = new ArrayList<>();
        if (extensionsNode != null) {
            for (final AsdfNode extensionNode : extensionsNode) {
                extensions.add(new Extension100(extensionNode));
            }
        }

        final List<HistoryEntry> historyEntries = new ArrayList<>();
        if (entriesNode != null) {
            for (final AsdfNode entryNode : entriesNode) {
                historyEntries.add(new HistoryEntry100(entryNode));
            }
        }

        return new AsdfMetadataImpl(
                lowLevelFormat.getVersion(),
                lowLevelFormat.getAsdfStandardVersion(),
                asdfLibrary,
                extensions,
                historyEntries
        );
    }

    @Override
    public AsdfNode getTree(final AsdfNode rawTree) {
        if (!rawTree.isMapping() || !rawTree.getTag().equals(ASDF_TAG)) {
            throw new IllegalStateException("Corrupted ASDF file: Top-level node must be a mapping with tag " + ASDF_TAG);
        }

        final MappingAsdfNode rawMappingTree = (MappingAsdfNode) rawTree;
        final Map<AsdfNode, AsdfNode> newValue = new HashMap<>(rawMappingTree.getValue());

        newValue.remove(StringAsdfNode.of("asdf_library"));
        newValue.remove(StringAsdfNode.of("history"));

        return new MappingAsdfNode(rawTree.getTag(), newValue);
    }
}
