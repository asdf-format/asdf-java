package org.asdfformat.asdf.standard;

import org.asdfformat.asdf.io.LowLevelFormat;
import org.asdfformat.asdf.metadata.AsdfMetadata;
import org.asdfformat.asdf.ndarray.UntypedNdArray;
import org.asdfformat.asdf.node.AsdfNode;
import org.asdfformat.asdf.node.impl.NdArrayAsdfNode;
import org.asdfformat.asdf.util.Version;

import java.util.Set;

public interface AsdfStandard {
    Version getVersion();

    AsdfMetadata getAsdfMetadata(LowLevelFormat lowLevelFormat, AsdfNode rawTree);

    AsdfNode getTree(AsdfNode rawTree);

    Set<String> getNdArrayTags();

    UntypedNdArray createNdArray(LowLevelFormat lowLevelFormat, NdArrayAsdfNode node);
}
