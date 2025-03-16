package org.asdfformat.asdf.metadata.impl;

import org.asdfformat.asdf.metadata.Extension;
import org.asdfformat.asdf.metadata.Software;
import org.asdfformat.asdf.node.AsdfNode;

public class Extension100 implements Extension {
    public static final String TAG = "tag:stsci.edu:asdf/core/extension_metadata-1.0.0";

    private final AsdfNode inner;

    public Extension100(final AsdfNode inner) {
        this.inner = inner;
    }

    @Override
    public String getExtensionClass() {
        return inner.containsKey("extension_class") ? inner.getString("extension_class") : null;
    }

    @Override
    public String getExtensionUri() {
        return inner.containsKey("extension_uri") ? inner.getString("extension_uri") : null;
    }

    @Override
    public Software getSoftware() {
        return inner.containsKey("software") ? new Software100(inner.get("software")) : null;
    }

    @Override
    public String toString() {
        return String.format(
                "Extension(extensionUri=%s)",
                getExtensionUri()
        );
    }
}
