package org.asdfformat.asdf.node.impl;

import org.asdfformat.asdf.node.AsdfNodeType;
import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.nodes.ScalarNode;

public class NullAsdfNode extends AsdfNodeBase {
    private final ScalarNode inner;

    public NullAsdfNode(final ScalarNode inner) {
        this.inner = inner;
    }

    @Override
    protected Node getInner() {
        return inner;
    }

    @Override
    public AsdfNodeType getNodeType() {
        return AsdfNodeType.NULL;
    }

    @Override
    public boolean isNull() {
        return true;
    }
}
