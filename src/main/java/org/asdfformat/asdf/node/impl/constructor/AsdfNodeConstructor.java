package org.asdfformat.asdf.node.impl.constructor;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

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

    public AsdfNodeConstructor(final LoaderOptions loaderOptions) {
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
            return new BooleanAsdfNode((ScalarNode) node);
        }
    }

    public class ConstructMappingAsdfNode extends AbstractConstruct {

        @Override
        public Object construct(final Node node) {
            assert !node.isTwoStepsConstruction();

            final Map<AsdfNode, AsdfNode> value = new LinkedHashMap<>();
            for (final Entry<Object, Object> entry : AsdfNodeConstructor.this.constructMapping((MappingNode)node).entrySet()) {
                value.put((AsdfNode)entry.getKey(), (AsdfNode)entry.getValue());
            }

            return new MappingAsdfNode((MappingNode) node, value);
        }
    }

    public class ConstructNdArrayAsdfNode extends AbstractConstruct {

        @Override
        public Object construct(final Node node) {
            return new NdArrayAsdfNode((MappingNode) node);
        }
    }

    public class ConstructNullAsdfNode extends AbstractConstruct {

        @Override
        public Object construct(final Node node) {
            return new NullAsdfNode((ScalarNode) node);
        }
    }

    public class ConstructNumberAsdfNode extends AbstractConstruct {

        @Override
        public Object construct(final Node node) {
            return new NumberAsdfNode((ScalarNode) node);
        }
    }

    public class ConstructSequenceAsdfNode extends AbstractConstruct {

        @Override
        public Object construct(final Node node) {
            return new SequenceAsdfNode((SequenceNode) node);
        }
    }


    public class ConstructStringAsdfNode extends AbstractConstruct {

        @Override
        public Object construct(final Node node) {
            return new StringAsdfNode((ScalarNode) node);
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
