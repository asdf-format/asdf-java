package org.asdfformat.asdf.node.impl;

import org.asdfformat.asdf.node.AsdfNodeType;
import org.yaml.snakeyaml.nodes.ScalarNode;
import org.yaml.snakeyaml.nodes.Tag;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;


public class NumberAsdfNode extends AsdfNodeBase {
    private static final Map<Class<?>, Function<Object, BigDecimal>> BIG_DECIMAL_CONVERTERS;
    static {
        BIG_DECIMAL_CONVERTERS = new HashMap<>();
        BIG_DECIMAL_CONVERTERS.put(BigDecimal.class, o -> (BigDecimal) o);
        BIG_DECIMAL_CONVERTERS.put(Double.class, o -> BigDecimal.valueOf((Double) o));
        BIG_DECIMAL_CONVERTERS.put(Float.class, o -> BigDecimal.valueOf((Float) o));
        BIG_DECIMAL_CONVERTERS.put(BigInteger.class, o -> new BigDecimal((BigInteger) o));
        BIG_DECIMAL_CONVERTERS.put(Long.class, o -> BigDecimal.valueOf((Long) o));
        BIG_DECIMAL_CONVERTERS.put(Integer.class, o -> BigDecimal.valueOf((Integer) o));
        BIG_DECIMAL_CONVERTERS.put(Short.class, o -> BigDecimal.valueOf((Short) o));
        BIG_DECIMAL_CONVERTERS.put(Byte.class, o -> BigDecimal.valueOf((Byte) o));
    }

    private static final Map<Class<?>, Function<Object, BigInteger>> BIG_INTEGER_CONVERTERS;
    static {
        BIG_INTEGER_CONVERTERS = new HashMap<>();
        BIG_INTEGER_CONVERTERS.put(BigInteger.class, o -> (BigInteger) o);
        BIG_INTEGER_CONVERTERS.put(Long.class, o -> BigInteger.valueOf((Long) o));
        BIG_INTEGER_CONVERTERS.put(Integer.class, o -> BigInteger.valueOf((Integer) o));
        BIG_INTEGER_CONVERTERS.put(Short.class, o -> BigInteger.valueOf((Short) o));
        BIG_INTEGER_CONVERTERS.put(Byte.class, o -> BigInteger.valueOf((Byte) o));
    }

    public static NumberAsdfNode of(final ScalarNode scalarNode, final Number value) {
        return new NumberAsdfNode(scalarNode.getTag().getValue(), scalarNode.getValue(), value);
    }

    public static NumberAsdfNode of(final Number value) {
        if (value instanceof Byte || value instanceof Short || value instanceof Integer || value instanceof BigInteger) {
            return new NumberAsdfNode(Tag.INT.getValue(), value.toString(), value);
        } else if (value instanceof Float || value instanceof Double || value instanceof BigDecimal) {
            return new NumberAsdfNode(Tag.FLOAT.getValue(), value.toString(), value);
        } else {
            throw new IllegalArgumentException("Unexpected Number subclass: " + value.getClass().getName());
        }
    }

    private final String tag;
    private final String rawValue;
    private final Number value;

    public NumberAsdfNode(final String tag, final String rawValue, final Number value) {
        this.tag = tag;
        this.rawValue = rawValue;
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
        final Function<Object, BigDecimal> converter = BIG_DECIMAL_CONVERTERS.get(value.getClass());
        if (converter == null) {
            throw new RuntimeException("Node cannot be represented as BigDecimal");
        }
        return converter.apply(value);
    }

    @Override
    public BigInteger asBigInteger() {
        final Function<Object, BigInteger> converter = BIG_INTEGER_CONVERTERS.get(value.getClass());
        if (converter == null) {
            throw new RuntimeException("Node cannot be represented as BigInteger");
        }
        return converter.apply(value);
    }

    @Override
    public byte asByte() {
        return value.byteValue();
    }

    @Override
    public double asDouble() {
        return value.doubleValue();
    }

    @Override
    public float asFloat() {
        return value.floatValue();
    }

    @Override
    public int asInt() {
        return value.intValue();
    }

    @Override
    public long asLong() {
        return value.longValue();
    }

    @Override
    public Number asNumber() {
        return value;
    }

    @Override
    public short asShort() {
        return value.shortValue();
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
        return Objects.equals(tag, typedOther.tag) && Objects.equals(value, typedOther.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tag, value);
    }

    @Override
    public String toString() {
        final List<String> fields = new ArrayList<>();

        if (!(tag.equals(Tag.INT.getValue()) || tag.equals(Tag.FLOAT.getValue()))) {
            fields.add("tag");
            fields.add(tag);
        }

        fields.add("value");
        fields.add(rawValue);

        return NodeUtils.nodeToString(this, fields);
    }
}
