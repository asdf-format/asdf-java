package org.asdfformat.asdf.node.impl;

import org.asdfformat.asdf.node.AsdfNodeType;
import org.yaml.snakeyaml.nodes.ScalarNode;
import org.yaml.snakeyaml.nodes.Tag;

import java.util.*;


public class BooleanAsdfNode extends AsdfNodeBase {
    public static final Map<String, Boolean> BOOLEAN_VALUES = new HashMap<>();
    static {
        BOOLEAN_VALUES.put("yes", Boolean.TRUE);
        BOOLEAN_VALUES.put("no", Boolean.FALSE);
        BOOLEAN_VALUES.put("true", Boolean.TRUE);
        BOOLEAN_VALUES.put("false", Boolean.FALSE);
        BOOLEAN_VALUES.put("on", Boolean.TRUE);
        BOOLEAN_VALUES.put("off", Boolean.FALSE);
    }

    public static BooleanAsdfNode of(final ScalarNode node) {
        return new BooleanAsdfNode(node.getTag().getValue(), node.getValue());
    }

    public static BooleanAsdfNode of(final boolean value) {
        return new BooleanAsdfNode(Tag.BOOL.getValue(), Boolean.toString(value));
    }

    private final String tag;
    private final String value;

    public BooleanAsdfNode(final String tag, final String value) {
        this.tag = tag;
        this.value = value;
    }

    @Override
    public AsdfNodeType getNodeType() {
        return AsdfNodeType.BOOLEAN;
    }

    @Override
    public String getTag() {
        return tag;
    }

    @Override
    public boolean asBoolean() {
        final Boolean result = BOOLEAN_VALUES.get(value.toLowerCase());
        if (result == null) {
            throw new RuntimeException(String.format("Unrecognized boolean value: %s", result));
        }
        return result;
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        final BooleanAsdfNode typedOther = (BooleanAsdfNode) other;
        return Objects.equals(tag, typedOther.tag) && Objects.equals(asBoolean(), typedOther.asBoolean());
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
        fields.add(value);

        return NodeUtils.nodeToString(this, fields);
    }
}
