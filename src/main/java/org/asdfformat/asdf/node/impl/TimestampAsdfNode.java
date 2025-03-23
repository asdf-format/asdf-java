package org.asdfformat.asdf.node.impl;

import org.asdfformat.asdf.node.AsdfNodeType;
import org.yaml.snakeyaml.nodes.ScalarNode;
import org.yaml.snakeyaml.nodes.Tag;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TimestampAsdfNode extends AsdfNodeBase {
    public static TimestampAsdfNode of(final ScalarNode node, final Instant value) {
        return new TimestampAsdfNode(node.getTag().getValue(), value);
    }

    public static TimestampAsdfNode of(final Instant value) {
        return new TimestampAsdfNode(Tag.TIMESTAMP.getValue(), value);
    }

    private final String tag;
    private final Instant value;

    public TimestampAsdfNode(final String tag, final Instant value) {
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
    public Instant asInstant() {
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
        final TimestampAsdfNode typedOther = (TimestampAsdfNode) other;
        return Objects.equals(tag, typedOther.tag) && Objects.equals(value, typedOther.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tag, value);
    }

    @Override
    public String toString() {
        final List<String> fields = new ArrayList<>();

        if (!tag.equals(Tag.STR.getValue())) {
            fields.add("tag");
            fields.add(tag);
        }

        fields.add("value");
        fields.add(value.toString());

        return NodeUtils.nodeToString(this, fields);
    }
}
