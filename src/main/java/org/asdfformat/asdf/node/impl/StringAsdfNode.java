package org.asdfformat.asdf.node.impl;

import java.util.Objects;

import org.asdfformat.asdf.node.AsdfNodeType;
import org.yaml.snakeyaml.nodes.ScalarNode;
import org.yaml.snakeyaml.nodes.Tag;

public class StringAsdfNode extends AsdfNodeBase {
    public static StringAsdfNode of(final ScalarNode node) {
        return new StringAsdfNode(node.getTag().getValue(), node.getValue());
    }

    public static StringAsdfNode of(final String value) {
        return new StringAsdfNode(Tag.STR.getValue(), value);
    }

    private final String tag;
    private final String value;

    public StringAsdfNode(final String tag, final String value) {
        this.tag = tag;
        this.value = value;
    }

    @Override
    public AsdfNodeType getNodeType() {
        return AsdfNodeType.STRING;
    }

    @Override
    public String getTag() {
        return tag;
    }

    @Override
    public String asString() {
        return value;
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        final StringAsdfNode typedOther = (StringAsdfNode) other;
        return Objects.equals(tag, typedOther.tag) && Objects.equals(value, typedOther.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tag, value);
    }

    @Override
    public String toString() {
        return "StringAsdfNode{" +
            "tag='" + tag + '\'' +
            ", value='" + value + '\'' +
            '}';
    }
}
