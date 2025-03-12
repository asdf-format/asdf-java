package org.asdfformat.asdf.node.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;

import org.asdfformat.asdf.node.AsdfNode;
import org.asdfformat.asdf.node.AsdfNodeType;
import org.yaml.snakeyaml.nodes.SequenceNode;

public class SequenceAsdfNode extends AsdfNodeBase {

    public static SequenceAsdfNode of(final SequenceNode node, final List<AsdfNode> value) {
        return new SequenceAsdfNode(node.getTag().getValue(), value);
    }

    private final String tag;
    private final List<AsdfNode> value;

    public SequenceAsdfNode(final String tag, final List<AsdfNode> value) {
        this.tag = tag;
        this.value = value;
    }

    @Override
    public AsdfNodeType getNodeType() {
        return AsdfNodeType.SEQUENCE;
    }

    @Override
    public String getTag() {
        return tag;
    }

    @Override
    public AsdfNode get(final long key) {
        return value.get((int)key);
    }

    @Override
    public AsdfNode get(final AsdfNode key) {
        return value.get(key.asInt());
    }

    @Override
    public <T> List<T> asList(final Class<T> elementClass) {
        final Function<AsdfNode, T> converter = NodeUtils.getConverterTo(elementClass);

        final List<T> result = new ArrayList<>();
        for (final AsdfNode element : value) {
            result.add(converter.apply(element));
        }

        return result;
    }

    @Override
    public Iterator<AsdfNode> iterator() {
        return value.iterator();
    }
}
