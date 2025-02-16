package org.asdfformat.asdf;

import java.nio.file.Path;


public class Test {
    public static void main(final String[] args) {
        final Asdf asdf;
        final Path path;

        try (final AsdfFile asdfFile = asdf.open(path)) {
            final float[][] data = asdfFile.getTree()
                .get("roman")
                .getNdArray("data")
                .asFloatNdArray()
                .toArray(new float[0][0]);


        }
    }
}
