package org.asdfformat.asdf.node.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Objects;

import org.asdfformat.asdf.node.AsdfNodeType;
import org.yaml.snakeyaml.nodes.ScalarNode;
import org.yaml.snakeyaml.nodes.Tag;


public class NumberAsdfNode extends AsdfNodeBase {
    private final String tag;
    private final String value;

    public static NumberAsdfNode of(final ScalarNode scalarNode) {
        return new NumberAsdfNode(scalarNode.getTag().getValue(), scalarNode.getValue());
    }

    public static NumberAsdfNode of(final long value) {
        return new NumberAsdfNode(Tag.INT.getValue(), Long.toString(value));
    }

    public NumberAsdfNode(final String tag, final String value) {
        this.tag = tag;
        this.value = value;
    }

    @Override
    public AsdfNodeType getNodeType() {
        return AsdfNodeType.NUMBER;
    }

    @Override
    public String getTag() {
        return tag;
    }

    @Override
    public BigDecimal asBigDecimal() {
        throw new RuntimeException("Not yet implemented");
    }

    @Override
    public BigInteger asBigInteger() {
        throw new RuntimeException("Not yet implemented");
    }

    @Override
    public byte asByte() {
        throw new RuntimeException("Not yet implemented");
    }

    @Override
    public double asDouble() {
        throw new RuntimeException("Not yet implemented");
    }

    @Override
    public float asFloat() {
        throw new RuntimeException("Not yet implemented");
    }

    @Override
    public int asInt() {
        throw new RuntimeException("Not yet implemented");
    }

    @Override
    public long asLong() {
        throw new RuntimeException("Not yet implemented");
    }

    @Override
    public Number asNumber() {
        throw new RuntimeException("Not yet implemented");
    }

    @Override
    public short asShort() {
        throw new RuntimeException("Not yet implemented");
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        final NumberAsdfNode typedOther = (NumberAsdfNode) other;
        // TODO: Not correct yet
        return Objects.equals(tag, typedOther.tag) && Objects.equals(value, typedOther.value);
    }

    @Override
    public int hashCode() {
        // TODO: Not correct yet
        return Objects.hash(tag, value);
    }
}
