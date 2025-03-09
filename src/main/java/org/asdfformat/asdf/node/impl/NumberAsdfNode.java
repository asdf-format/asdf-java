package org.asdfformat.asdf.node.impl;

import java.math.BigDecimal;
import java.math.BigInteger;

import org.asdfformat.asdf.node.AsdfNodeType;
import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.nodes.ScalarNode;


public class NumberAsdfNode extends AsdfNodeBase {
    private final ScalarNode inner;

    public NumberAsdfNode(final ScalarNode inner) {
        this.inner = inner;
    }

    @Override
    public AsdfNodeType getNodeType() {
        return AsdfNodeType.NUMBER;
    }

    @Override
    protected Node getInner() {
        return inner;
    }

    @Override
    public boolean isNumber() {
        return true;
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
        throw new RuntimeException("Not yet implemented");    }

    @Override
    public Number asNumber() {
        throw new RuntimeException("Not yet implemented");
    }

    @Override
    public short asShort() {
        throw new RuntimeException("Not yet implemented");
    }
}
