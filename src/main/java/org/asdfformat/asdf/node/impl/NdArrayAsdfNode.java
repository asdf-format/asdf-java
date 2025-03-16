package org.asdfformat.asdf.node.impl;

import org.asdfformat.asdf.io.LowLevelFormat;
import org.asdfformat.asdf.ndarray.NdArray;
import org.asdfformat.asdf.node.AsdfNode;
import org.asdfformat.asdf.node.AsdfNodeType;
import org.asdfformat.asdf.standard.AsdfStandard;

import java.util.Map;


public class NdArrayAsdfNode extends MappingAsdfNode {
    private final LowLevelFormat lowLevelFormat;
    private final AsdfStandard asdfStandard;

    public NdArrayAsdfNode(final String tag, final Map<AsdfNode, AsdfNode> value, final LowLevelFormat lowLevelFormat, final AsdfStandard asdfStandard) {
        super(tag, value);

        this.lowLevelFormat = lowLevelFormat;
        this.asdfStandard = asdfStandard;

        if (!asdfStandard.getNdArrayTags().contains(getTag())) {
            throw new IllegalStateException(String.format("Tag %s is not a valid ndarray tag for ASDF Standard version %s", getTag(), asdfStandard.getVersion()));
        }
    }

    @Override
    public AsdfNodeType getNodeType() {
        return AsdfNodeType.NDARRAY;
    }

    @Override
    public NdArray<?> asNdArray() {
        return asdfStandard.createNdArray(lowLevelFormat, this);
    }
}
