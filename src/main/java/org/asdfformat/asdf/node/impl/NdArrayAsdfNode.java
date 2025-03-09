package org.asdfformat.asdf.node.impl;

import java.util.HashSet;
import java.util.Set;

import org.asdfformat.asdf.ndarray.NdArray;
import org.asdfformat.asdf.node.AsdfNodeType;
import org.yaml.snakeyaml.nodes.MappingNode;
import org.yaml.snakeyaml.nodes.Node;


public class NdArrayAsdfNode extends AsdfNodeBase {
    public static final Set<String> TAGS = new HashSet<>();
    static {
        TAGS.add("tag:stsci.edu:asdf/core/ndarray-1.0.0");
        TAGS.add("tag:stsci.edu:asdf/core/ndarray-1.1.0");
    }

    private final MappingNode inner;

    public NdArrayAsdfNode(final MappingNode inner) {
        this.inner = inner;
    }

    @Override
    public AsdfNodeType getNodeType() {
        return AsdfNodeType.NDARRAY;
    }

    @Override
    protected Node getInner() {
        return inner;
    }

    @Override
    public NdArray<?> asNdArray() {
        throw new RuntimeException("Not implemented yet");
    }
}
