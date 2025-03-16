package org.asdfformat.asdf.node.impl;

import org.asdfformat.asdf.node.AsdfNode;
import org.asdfformat.asdf.node.AsdfNodeType;
import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.nodes.Tag;

import java.util.*;
import java.util.Map.Entry;
import java.util.function.Function;

public class MappingAsdfNode extends AsdfNodeBase {

    public static MappingAsdfNode of(final Node node, final Map<AsdfNode, AsdfNode> value) {
        return new MappingAsdfNode(node.getTag().getValue(), value);
    }

    private final String tag;
    private final Map<AsdfNode, AsdfNode> value;

    public MappingAsdfNode(final String tag, final Map<AsdfNode, AsdfNode> value) {
        this.tag = tag;
        this.value = value;
    }

    @Override
    public String getTag() {
        return tag;
    }

    public Map<AsdfNode, AsdfNode> getValue() {
        return value;
    }

    @Override
    public AsdfNodeType getNodeType() {
        return AsdfNodeType.MAPPING;
    }

    @Override
    public boolean containsKey(final AsdfNode key) {
        return value.containsKey(key);
    }

    @Override
    public int size() {
        return value.size();
    }

    @Override
    public AsdfNode get(final String key) {
        final AsdfNode result = value.get(StringAsdfNode.of(key));
        if (result == null) {
            throw new IllegalArgumentException(String.format("Key does not exist: %s", key));
        }
        return result;
    }

    @Override
    public AsdfNode get(final long key) {
        final AsdfNode result = value.get(NumberAsdfNode.of(key));
        if (result == null) {
            throw new IllegalArgumentException(String.format("Key does not exist: %s", key));
        }
        return result;
    }

    @Override
    public AsdfNode get(final boolean key) {
        final AsdfNode result = value.get(BooleanAsdfNode.of(key));
        if (result == null) {
            throw new IllegalArgumentException(String.format("Key does not exist: %s", key));
        }
        return result;
    }

    @Override
    public AsdfNode get(final AsdfNode key) {
        final AsdfNode result = value.get(key);
        if (result == null) {
            throw new IllegalArgumentException(String.format("Key does not exist: %s", key));
        }
        return result;
    }

    @Override
    public <K, V> Map<K, V> asMap(final Class<K> keyClass, final Class<V> valueClass) {
        final Function<AsdfNode, K> keyConverter = NodeUtils.getConverterTo(keyClass);
        final Function<AsdfNode, V> valueConverter = NodeUtils.getConverterTo(valueClass);

        final Map<K, V> result = new LinkedHashMap<>();
        for (final Entry<AsdfNode, AsdfNode> entry : value.entrySet()) {
            result.put(keyConverter.apply(entry.getKey()), valueConverter.apply(entry.getValue()));
        }

        return result;
    }

    @Override
    public Iterator<AsdfNode> iterator() {
        return value.keySet().iterator();
    }

    @Override
    public String toString() {
        final List<String> fields = new ArrayList<>();

        if (!tag.equals(Tag.MAP.getValue())) {
            fields.add("tag");
            fields.add(tag);
        }

        fields.add("size");
        fields.add(String.valueOf(value.size()));

        return NodeUtils.nodeToString(this, fields);
    }
}
