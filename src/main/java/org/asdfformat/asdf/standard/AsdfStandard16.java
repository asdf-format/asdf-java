package org.asdfformat.asdf.standard;

import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import org.asdfformat.asdf.io.LowLevelFormat;
import org.asdfformat.asdf.metadata.AsdfMetadata;
import org.asdfformat.asdf.node.AsdfNode;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.nodes.Node;

public class AsdfStandard16 implements AsdfStandard {
    private static final String NDARRAY_TAG = "tag:stsci.edu:asdf/core/ndarray-1.1.0";

    public AsdfStandard fromLowLevelFormat(final LowLevelFormat lowLevelFormat) {
        lowLevelFormat.getTreeBytes();
        final Yaml yaml = new Yaml();

        System.out.println(new String(lowLevelFormat.getTreeBytes(), StandardCharsets.UTF_8));

        final Node node = yaml.compose(new InputStreamReader(new ByteArrayInputStream(lowLevelFormat.getTreeBytes())));

        return null;
    }

    @Override
    public AsdfMetadata getMetadata() {
        return null;
    }

    @Override
    public AsdfNode getTree() {
        return null;
    }
}
