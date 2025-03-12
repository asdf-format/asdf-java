package org.asdfformat.asdf.node.impl;

import java.util.HashSet;
import java.util.Set;

import org.asdfformat.asdf.io.LowLevelFormat;
import org.asdfformat.asdf.ndarray.NdArray;
import org.asdfformat.asdf.node.AsdfNodeType;
import org.yaml.snakeyaml.nodes.MappingNode;


public class NdArrayAsdfNode extends AsdfNodeBase {
    public static final Set<String> TAGS = new HashSet<>();
    static {
        TAGS.add("tag:stsci.edu:asdf/core/ndarray-1.0.0");
        TAGS.add("tag:stsci.edu:asdf/core/ndarray-1.1.0");
    }

    private final MappingNode    inner;
    private final LowLevelFormat lowLevelFormat;

    public NdArrayAsdfNode(final MappingNode inner, final LowLevelFormat lowLevelFormat) {
        this.inner = inner;
        this.lowLevelFormat = lowLevelFormat;
    }

    @Override
    public AsdfNodeType getNodeType() {
        return AsdfNodeType.NDARRAY;
    }

    @Override
    public String getTag() {
        return inner.getTag().getValue();
    }

    @Override
    public NdArray<?> asNdArray() {
        throw new RuntimeException("Not implemented yet");
    }
}
