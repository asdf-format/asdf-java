package org.asdfformat.asdf.standard;

import org.asdfformat.asdf.util.Version;

import java.util.HashMap;
import java.util.Map;

public class AsdfStandards {
    private static final Map<Version, AsdfStandard> ASDF_STANDARDS;
    static {
        ASDF_STANDARDS = new HashMap<>();
        ASDF_STANDARDS.put(AsdfStandardV1_6_0.VERSION, new AsdfStandardV1_6_0());
    }

    public static AsdfStandard of(final Version version) {
        final AsdfStandard asdfStandard = ASDF_STANDARDS.get(version);
        if (asdfStandard == null) {
            throw new IllegalArgumentException(String.format("This library does not support ASDF Standard %s", version));
        }
        return asdfStandard;
    }
}
