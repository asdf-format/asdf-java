package org.asdfformat.asdf.standard;

import org.asdfformat.asdf.io.LowLevelFormat;
import org.asdfformat.asdf.metadata.AsdfMetadata;
import org.asdfformat.asdf.metadata.Extension;
import org.asdfformat.asdf.metadata.HistoryEntry;
import org.asdfformat.asdf.metadata.Software;
import org.asdfformat.asdf.metadata.impl.AsdfMetadataImpl;
import org.asdfformat.asdf.metadata.impl.Extension100;
import org.asdfformat.asdf.metadata.impl.HistoryEntry100;
import org.asdfformat.asdf.metadata.impl.Software100;
import org.asdfformat.asdf.ndarray.DataType;
import org.asdfformat.asdf.ndarray.NdArray;
import org.asdfformat.asdf.ndarray.impl.NdArrayImpl;
import org.asdfformat.asdf.node.AsdfNode;
import org.asdfformat.asdf.node.impl.MappingAsdfNode;
import org.asdfformat.asdf.node.impl.NdArrayAsdfNode;
import org.asdfformat.asdf.node.impl.StringAsdfNode;
import org.asdfformat.asdf.util.Version;

import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class AsdfStandardV1_5_0 implements AsdfStandard {
    public static final Version VERSION = Version.fromString("1.5.0");
    private final Set<String> NDARRAY_TAGS = new HashSet<>(Arrays.asList("tag:stsci.edu:asdf/core/ndarray-1.0.0"));
    private final String ASDF_TAG = "tag:stsci.edu:asdf/core/asdf-1.1.0";

    @Override
    public Version getVersion() {
        return VERSION;
    }

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

    @Override
    public Set<String> getNdArrayTags() {
        return NDARRAY_TAGS;
    }

    @Override
    public NdArray<?> createNdArray(final LowLevelFormat lowLevelFormat, final NdArrayAsdfNode node) {
        if (node.containsKey("data")) {
            throw new RuntimeException("Support for ndarray with inline data is not implemented yet");
        }

        if (!node.get("datatype").isString()) {
            throw new RuntimeException("Support for ndarray with structured datatype is not implemented yet");
        }

        final DataType dataType = DataType.fromString(node.getString("datatype"));

        if (!node.get("source").isNumber()) {
            throw new RuntimeException("Support for ndarray with external array source is not implemented yet");
        }

        final int source = node.getInt("source");

        final int[] shape = new int[node.get("shape").size()];
        for (int i = 0; i < shape.length; i++) {
            final AsdfNode shapeNode = node.get("shape").get(i);
            if (shapeNode.isString()) {
                throw new RuntimeException("Support for streaming ndarray is not implemented yet");
            }
            shape[i] = shapeNode.asInt();
        }

        final String byteOrderValue = node.getString("byteorder");
        final ByteOrder byteOrder;
        if (byteOrderValue.equals("big")) {
            byteOrder = ByteOrder.BIG_ENDIAN;
        } else if (byteOrderValue.equals("little")) {
            byteOrder = ByteOrder.LITTLE_ENDIAN;
        } else {
            throw new RuntimeException("Unhandled ndarray byte order: " + byteOrderValue);
        }

        final int offset;
        if (node.containsKey("offset")) {
            offset = node.getInt("offset");
        } else {
            offset = 0;
        }

        final int[] strides = new int[shape.length];
        if (node.containsKey("strides")) {
            for (int i = 0; i < strides.length; i++) {
                strides[i] = node.get("strides").getInt(i);
            }
        } else {
            int nextStrides = dataType.getWidthBytes();
            for (int i = shape.length - 1; i >= 0; i--) {
                strides[i] = nextStrides;
                nextStrides *= shape[i];
            }
        }

        if (node.containsKey("mask")) {
            throw new RuntimeException("Support for masked ndarray not implemented yet");
        }

        return new NdArrayImpl(
                dataType,
                shape,
                byteOrder,
                strides,
                offset,
                source,
                lowLevelFormat
        );
    }
}
