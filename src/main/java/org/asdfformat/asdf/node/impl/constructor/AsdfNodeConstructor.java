package org.asdfformat.asdf.node.impl.constructor;

import org.asdfformat.asdf.io.LowLevelFormat;
import org.asdfformat.asdf.node.AsdfNode;
import org.asdfformat.asdf.node.impl.BooleanAsdfNode;
import org.asdfformat.asdf.node.impl.MappingAsdfNode;
import org.asdfformat.asdf.node.impl.NdArrayAsdfNode;
import org.asdfformat.asdf.node.impl.NullAsdfNode;
import org.asdfformat.asdf.node.impl.NumberAsdfNode;
import org.asdfformat.asdf.node.impl.SequenceAsdfNode;
import org.asdfformat.asdf.node.impl.StringAsdfNode;
import org.asdfformat.asdf.node.impl.TimestampAsdfNode;
import org.asdfformat.asdf.standard.AsdfStandard;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.constructor.AbstractConstruct;
import org.yaml.snakeyaml.constructor.SafeConstructor;
import org.yaml.snakeyaml.nodes.MappingNode;
import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.nodes.NodeId;
import org.yaml.snakeyaml.nodes.ScalarNode;
import org.yaml.snakeyaml.nodes.SequenceNode;
import org.yaml.snakeyaml.nodes.Tag;
import org.yaml.snakeyaml.resolver.Resolver;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class AsdfNodeConstructor extends SafeConstructor {

    public AsdfNodeConstructor(final LoaderOptions loaderOptions, final Resolver resolver, final LowLevelFormat lowLevelFormat, final AsdfStandard asdfStandard) {
        super(loaderOptions);

        this.yamlConstructors.clear();
        this.yamlClassConstructors.clear();

        this.yamlConstructors.put(Tag.BOOL, new ConstructBooleanAsdfNode());
        this.yamlConstructors.put(Tag.INT, new ConstructNumberAsdfNode(new ConstructYamlInt()));
        this.yamlConstructors.put(Tag.FLOAT, new ConstructNumberAsdfNode(new ConstructYamlFloat()));
        this.yamlConstructors.put(Tag.NULL, new ConstructNullAsdfNode());
        this.yamlConstructors.put(Tag.STR, new ConstructStringAsdfNode());
        this.yamlConstructors.put(Tag.TIMESTAMP, new ConstructTimestampAsdfNode(new ConstructYamlTimestamp()));
        this.yamlClassConstructors.put(NodeId.scalar, new ConstructScalarAsdfNode(resolver));

        this.yamlConstructors.put(Tag.SEQ, new ConstructSequenceAsdfNode());
        this.yamlClassConstructors.put(NodeId.sequence, new ConstructSequenceAsdfNode());

        this.yamlConstructors.put(Tag.MAP, new ConstructMappingAsdfNode());
        this.yamlClassConstructors.put(NodeId.mapping, new ConstructMappingAsdfNode());

        for (final String tag : asdfStandard.getNdArrayTags()) {
            this.yamlConstructors.put(new Tag(tag), new ConstructNdArrayAsdfNode(lowLevelFormat, asdfStandard));
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
        private final AsdfStandard asdfStandard;

        public ConstructNdArrayAsdfNode(final LowLevelFormat lowLevelFormat, final AsdfStandard asdfStandard) {
            this.lowLevelFormat = lowLevelFormat;
            this.asdfStandard = asdfStandard;
        }

        @Override
        public Object construct(final Node node) {
            assert !node.isTwoStepsConstruction();

            final Map<AsdfNode, AsdfNode> value = new LinkedHashMap<>();
            for (final Entry<Object, Object> entry : AsdfNodeConstructor.this.constructMapping((MappingNode) node).entrySet()) {
                value.put((AsdfNode) entry.getKey(), (AsdfNode) entry.getValue());
            }

            return new NdArrayAsdfNode(node.getTag().getValue(), value, lowLevelFormat, asdfStandard);
        }
    }

    public class ConstructNullAsdfNode extends AbstractConstruct {

        @Override
        public Object construct(final Node node) {
            return NullAsdfNode.of(node);
        }
    }

    public class ConstructNumberAsdfNode extends AbstractConstruct {
        private final AbstractConstruct constructYaml;

        public ConstructNumberAsdfNode(final AbstractConstruct constructYaml) {
            this.constructYaml = constructYaml;
        }

        @Override
        public Object construct(final Node node) {
            final Number value = (Number) constructYaml.construct(node);
            return NumberAsdfNode.of((ScalarNode) node, value);
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

    public class ConstructTimestampAsdfNode extends AbstractConstruct {
        private final AbstractConstruct constructYaml;

        public ConstructTimestampAsdfNode(final AbstractConstruct constructYaml) {
            this.constructYaml = constructYaml;
        }

        @Override
        public Object construct(final Node node) {
            final Date value = (Date) constructYaml.construct(node);

            return TimestampAsdfNode.of((ScalarNode) node, value.toInstant());
        }
    }

    public class ConstructScalarAsdfNode extends AbstractConstruct {
        private final Resolver resolver;

        public ConstructScalarAsdfNode(final Resolver resolver) {
            this.resolver = resolver;
        }

        @Override
        public Object construct(final Node node) {
            final ScalarNode scalarNode = (ScalarNode) node;

            final Tag tag = resolver.resolve(NodeId.scalar, scalarNode.getValue(), true);

            try {
                return AsdfNodeConstructor.this.yamlConstructors.get(tag).construct(node);
            } catch (final NullPointerException e) {
                System.out.println("Ut oh");
                throw e;
            }
        }
    }



    public class ConstructUnsupportedAsdfNode extends AbstractConstruct {

        @Override
        public Object construct(final Node node) {
            throw new RuntimeException("Not implemented yet");
        }
    }
}
