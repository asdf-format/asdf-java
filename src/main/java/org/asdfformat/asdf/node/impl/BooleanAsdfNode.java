package org.asdfformat.asdf.node.impl;

import java.util.HashMap;
import java.util.Map;

import org.asdfformat.asdf.node.AsdfNodeType;
import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.nodes.ScalarNode;

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
    private final ScalarNode inner;

    public BooleanAsdfNode(final ScalarNode inner) {
        this.inner = inner;
    }

    @Override
    public AsdfNodeType getNodeType() {
        return AsdfNodeType.BOOLEAN;
    }

    @Override
    protected Node getInner() {
        return inner;
    }

    @Override
    public boolean asBoolean() {
        final Boolean value = BOOLEAN_VALUES.get(inner.getValue());
        if (value == null) {
            throw new RuntimeException(String.format("Unrecognized boolean value: %s", inner.getValue()));
        }
        return value;
    }
}
