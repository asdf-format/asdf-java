package org.asdfformat.asdf.metadata.impl;

import lombok.RequiredArgsConstructor;
import org.asdfformat.asdf.metadata.Software;
import org.asdfformat.asdf.node.AsdfNode;

@RequiredArgsConstructor
public class Software100 implements Software {
    public static final String TAG = "tag:stsci.edu:asdf/core/software-1.0.0";

    private final AsdfNode inner;

    @Override
    public String getName() {
        return inner.containsKey("name") ? inner.getString("name") : null;
    }

    @Override
    public String getAuthor() {
        return inner.containsKey("author") ? inner.getString("author") : null;
    }

    @Override
    public String getHomepage() {
        return inner.containsKey("homepage") ? inner.getString("homepage") : null;
    }

    @Override
    public String getVersion() {
        return inner.containsKey("version") ? inner.getString("version") : null;
    }

    @Override
    public String toString() {
        return String.format(
                "Software(name=%s, version=%s)",
                getName(),
                getVersion()
        );
    }
}
