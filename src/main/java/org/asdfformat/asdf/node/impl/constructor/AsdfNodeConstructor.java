package org.asdfformat.asdf.node.impl.constructor;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.asdfformat.asdf.io.LowLevelFormat;
import org.asdfformat.asdf.node.AsdfNode;
import org.asdfformat.asdf.node.impl.BooleanAsdfNode;
import org.asdfformat.asdf.node.impl.MappingAsdfNode;
import org.asdfformat.asdf.node.impl.NdArrayAsdfNode;
import org.asdfformat.asdf.node.impl.NullAsdfNode;
import org.asdfformat.asdf.node.impl.NumberAsdfNode;
import org.asdfformat.asdf.node.impl.SequenceAsdfNode;
import org.asdfformat.asdf.node.impl.StringAsdfNode;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.constructor.AbstractConstruct;
import org.yaml.snakeyaml.constructor.BaseConstructor;

import org.yaml.snakeyaml.nodes.MappingNode;
import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.nodes.NodeId;
import org.yaml.snakeyaml.nodes.ScalarNode;
import org.yaml.snakeyaml.nodes.SequenceNode;
import org.yaml.snakeyaml.nodes.Tag;

public class AsdfNodeConstructor extends BaseConstructor {

    public AsdfNodeConstructor(final LoaderOptions loaderOptions, final LowLevelFormat lowLevelFormat) {
        super(loaderOptions);
        this.yamlConstructors.put(Tag.BOOL, new ConstructBooleanAsdfNode());
        this.yamlConstructors.put(Tag.INT, new ConstructNumberAsdfNode());
        this.yamlConstructors.put(Tag.FLOAT, new ConstructNumberAsdfNode());
        this.yamlConstructors.put(Tag.NULL, new ConstructNullAsdfNode());
        this.yamlConstructors.put(Tag.STR, new ConstructStringAsdfNode());
        this.yamlClassConstructors.put(NodeId.scalar, new ConstructScalarAsdfNode());

        this.yamlConstructors.put(Tag.SEQ, new ConstructSequenceAsdfNode());
        this.yamlClassConstructors.put(NodeId.sequence, new ConstructSequenceAsdfNode());

        this.yamlConstructors.put(Tag.MAP, new ConstructMappingAsdfNode());
        this.yamlClassConstructors.put(NodeId.mapping, new ConstructMappingAsdfNode());

        for (final String tag : NdArrayAsdfNode.TAGS) {
            this.yamlConstructors.put(new Tag(tag), new ConstructNdArrayAsdfNode(lowLevelFormat));
        }
    }

    @Override
    protected Object constructObject(final Node node) {
        if (!this.yamlConstructors.containsKey(node.getTag())) {
            node.setUseClassConstructor(true);
        }

        return super.constructObject(node);
    }

    @Override
    protected Map<Object, Object> constructMapping(final MappingNode node) {
        return super.constructMapping(node);
    }

    @Override
    protected void constructMapping2ndStep(final MappingNode node, final Map<Object, Object> mapping) {
        super.constructMapping2ndStep(node, mapping);
    }

    public class ConstructBooleanAsdfNode extends AbstractConstruct {

        @Override
        public Object construct(final Node node) {
            return BooleanAsdfNode.of((ScalarNode) node);
        }
    }

    public class ConstructMappingAsdfNode extends AbstractConstruct {

        @Override
        public Object construct(final Node node) {
            assert !node.isTwoStepsConstruction();

            final Map<AsdfNode, AsdfNode> value = new LinkedHashMap<>();
            for (final Entry<Object, Object> entry : AsdfNodeConstructor.this.constructMapping((MappingNode) node).entrySet()) {
                value.put((AsdfNode) entry.getKey(), (AsdfNode) entry.getValue());
            }

            return MappingAsdfNode.of(node, value);
        }
    }

    public class ConstructNdArrayAsdfNode extends AbstractConstruct {

        private final LowLevelFormat lowLevelFormat;

        public ConstructNdArrayAsdfNode(final LowLevelFormat lowLevelFormat) {
            this.lowLevelFormat = lowLevelFormat;
        }

        @Override
        public Object construct(final Node node) {
            return new NdArrayAsdfNode((MappingNode) node, lowLevelFormat);
        }
    }

    public class ConstructNullAsdfNode extends AbstractConstruct {

        @Override
        public Object construct(final Node node) {
            return NullAsdfNode.of(node);
        }
    }

    public class ConstructNumberAsdfNode extends AbstractConstruct {

        @Override
        public Object construct(final Node node) {
            return NumberAsdfNode.of((ScalarNode) node);
        }
    }

    public class ConstructSequenceAsdfNode extends AbstractConstruct {

        @Override
        public Object construct(final Node node) {
            assert !node.isTwoStepsConstruction();

            final List<AsdfNode> value = new ArrayList<>();
            for (final Object element : AsdfNodeConstructor.this.constructSequence((SequenceNode) node)) {
                value.add((AsdfNode) element);
            }

            return SequenceAsdfNode.of((SequenceNode) node, value);
        }
    }

    public class ConstructStringAsdfNode extends AbstractConstruct {

        @Override
        public Object construct(final Node node) {
            return StringAsdfNode.of((ScalarNode) node);
        }
    }

    public class ConstructScalarAsdfNode extends AbstractConstruct {

        @Override
        public Object construct(final Node node) {
            throw new RuntimeException("Not implemented yet");
        }
    }

    public class ConstructUnsupportedAsdfNode extends AbstractConstruct {

        @Override
        public Object construct(final Node node) {
            throw new RuntimeException("Not implemented yet");
        }
    }
}
