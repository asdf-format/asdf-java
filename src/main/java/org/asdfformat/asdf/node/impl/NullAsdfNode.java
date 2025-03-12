package org.asdfformat.asdf.node.impl;

import java.util.Objects;

import org.asdfformat.asdf.node.AsdfNodeType;
import org.yaml.snakeyaml.nodes.Node;

public class NullAsdfNode extends AsdfNodeBase {

    public static NullAsdfNode of(final Node node) {
        return new NullAsdfNode(node.getTag().getValue());
    }

    private final String tag;

    public NullAsdfNode(final String tag) {
        this.tag = tag;
    }

    @Override
    public AsdfNodeType getNodeType() {
        return AsdfNodeType.NULL;
    }

    @Override
    public String getTag() {
        return tag;
    }

    @Override
    public boolean isNull() {
        return true;
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        final NullAsdfNode typedOther = (NullAsdfNode) other;
        return Objects.equals(tag, typedOther.tag);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(tag);
    }
}
