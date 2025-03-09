package org.asdfformat.asdf.node.impl;

import java.util.Objects;

import org.asdfformat.asdf.node.AsdfNodeType;
import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.nodes.ScalarNode;

public class StringAsdfNode extends AsdfNodeBase {
    private final ScalarNode inner;

    public StringAsdfNode(final ScalarNode inner) {
        this.inner = inner;
    }

    @Override
    public AsdfNodeType getNodeType() {
        return AsdfNodeType.STRING;
    }

    @Override
    protected Node getInner() {
        return inner;
    }

    @Override
    public String asString() {
        return inner.getValue();
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }

        if (other == null) {
            return false;
        }

        if (other instanceof String) {
            return Objects.equals(asString(), other);
        }

        if (getClass() != other.getClass()) {
            return false;
        }

        final StringAsdfNode stringAsdfNode = (StringAsdfNode) other;
        return Objects.equals(asString(), stringAsdfNode.asString());
    }

    @Override
    public int hashCode() {
        return Objects.hash(asString());
    }
}
