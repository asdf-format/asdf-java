package org.asdfformat.asdf.node.impl;

import org.asdfformat.asdf.ndarray.NdArray;
import org.asdfformat.asdf.node.AsdfNode;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class NodeUtils {
    private static final Map<Class<?>, Function<AsdfNode, Object>> CONVERTERS;
    static {
        CONVERTERS = new HashMap<>();
        CONVERTERS.put(AsdfNode.class, n -> n);
        CONVERTERS.put(BigDecimal.class, AsdfNode::asBigDecimal);
        CONVERTERS.put(BigInteger.class, AsdfNode::asBigInteger);
        CONVERTERS.put(Boolean.class, AsdfNode::asBoolean);
        CONVERTERS.put(Byte.class, AsdfNode::asByte);
        CONVERTERS.put(Double.class, AsdfNode::asDouble);
        CONVERTERS.put(Float.class, AsdfNode::asFloat);
        CONVERTERS.put(Integer.class, AsdfNode::asInt);
        CONVERTERS.put(Long.class, AsdfNode::asLong);
        CONVERTERS.put(NdArray.class, AsdfNode::asNdArray);
        CONVERTERS.put(Number.class, AsdfNode::asNumber);
        CONVERTERS.put(Short.class, AsdfNode::asShort);
        CONVERTERS.put(String.class, AsdfNode::asString);
    }

    public static <T> Function<AsdfNode, T> getConverterTo(final Class<T> clazz) {
        final Function<AsdfNode, Object> converterFunc = CONVERTERS.get(clazz);
        if (converterFunc == null) {
            throw new IllegalArgumentException("Cannot represent node as " + clazz.getName());
        }
        return n -> clazz.cast(converterFunc.apply(n));
    }

    public static String nodeToString(final AsdfNode node, final List<String> fields) {
        final StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(node.getClass().getSimpleName());
        stringBuilder.append("(");

        if (fields.size() > 0) {
            for (int i = 0; i < fields.size(); i += 2) {
                stringBuilder.append(fields.get(i));
                stringBuilder.append("=");
                stringBuilder.append(fields.get(i + 1));
                stringBuilder.append(", ");
            }
            stringBuilder.setLength(stringBuilder.length() - 2);
        }

        stringBuilder.append(")");

        return stringBuilder.toString();
    }
}
