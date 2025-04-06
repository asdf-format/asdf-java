package org.asdfformat.asdf.standard;

import org.asdfformat.asdf.standard.impl.AsdfStandardImpl;
import org.asdfformat.asdf.standard.impl.NdArrayHandler_1_x;
import org.asdfformat.asdf.standard.impl.TreeHandler_1_1_0;
import org.asdfformat.asdf.util.Version;

import java.util.HashMap;
import java.util.Map;

public class AsdfStandards {
    private static final String NDARRAY_1_1_0_TAG = "tag:stsci.edu:asdf/core/ndarray-1.1.0";
    private static final String NDARRAY_1_0_0_TAG = "tag:stsci.edu:asdf/core/ndarray-1.0.0";

    private static final Map<Version, AsdfStandard> ASDF_STANDARDS;
    static {
        ASDF_STANDARDS = new HashMap<>();

        ASDF_STANDARDS.put(
                new Version(1, 6, 0),
                new AsdfStandardImpl(
                        new Version(1, 6, 0),
                        new TreeHandler_1_1_0(),
                        new NdArrayHandler_1_x(NDARRAY_1_1_0_TAG)
                )
        );

        ASDF_STANDARDS.put(
                new Version(1, 5, 0),
                new AsdfStandardImpl(
                        new Version(1, 5, 0),
                        new TreeHandler_1_1_0(),
                        new NdArrayHandler_1_x(NDARRAY_1_0_0_TAG)
                )
        );

        ASDF_STANDARDS.put(
                new Version(1, 4, 0),
                new AsdfStandardImpl(
                        new Version(1, 4, 0),
                        new TreeHandler_1_1_0(),
                        new NdArrayHandler_1_x(NDARRAY_1_0_0_TAG)
                )
        );

        ASDF_STANDARDS.put(
                new Version(1, 3, 0),
                new AsdfStandardImpl(
                        new Version(1, 3, 0),
                        new TreeHandler_1_1_0(),
                        new NdArrayHandler_1_x(NDARRAY_1_0_0_TAG)
                )
        );

        ASDF_STANDARDS.put(
                new Version(1, 2, 0),
                new AsdfStandardImpl(
                        new Version(1, 2, 0),
                        new TreeHandler_1_1_0(),
                        new NdArrayHandler_1_x(NDARRAY_1_0_0_TAG)
                )
        );
    }

    public static AsdfStandard of(final Version version) {
        final AsdfStandard asdfStandard = ASDF_STANDARDS.get(version);
        if (asdfStandard == null) {
            throw new IllegalArgumentException(String.format("This library does not support ASDF Standard %s", version));
        }
        return asdfStandard;
    }
}
