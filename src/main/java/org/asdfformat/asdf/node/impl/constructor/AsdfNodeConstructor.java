package org.asdfformat.asdf.node.impl.constructor;

import org.asdfformat.asdf.io.LowLevelFormat;
import org.asdfformat.asdf.node.AsdfNode;
import org.asdfformat.asdf.node.impl.*;
import org.asdfformat.asdf.standard.AsdfStandard;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.constructor.AbstractConstruct;
import org.yaml.snakeyaml.constructor.SafeConstructor;
import org.yaml.snakeyaml.nodes.*;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class AsdfNodeConstructor extends SafeConstructor {

    public AsdfNodeConstructor(final LoaderOptions loaderOptions, final LowLevelFormat lowLevelFormat, final AsdfStandard asdfStandard) {
        super(loaderOptions);

        this.yamlConstructors.clear();
        this.yamlClassConstructors.clear();

        this.yamlConstructors.put(Tag.BOOL, new ConstructBooleanAsdfNode());
        this.yamlConstructors.put(Tag.INT, new ConstructNumberAsdfNode(new ConstructYamlInt()));
        this.yamlConstructors.put(Tag.FLOAT, new ConstructNumberAsdfNode(new ConstructYamlFloat()));
        this.yamlConstructors.put(Tag.NULL, new ConstructNullAsdfNode());
        this.yamlConstructors.put(Tag.STR, new ConstructStringAsdfNode());
        this.yamlClassConstructors.put(NodeId.scalar, new ConstructScalarAsdfNode());

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
