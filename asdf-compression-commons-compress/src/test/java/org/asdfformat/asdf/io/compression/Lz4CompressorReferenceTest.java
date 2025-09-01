package org.asdfformat.asdf.io.compression;

import org.asdfformat.asdf.Asdf;
import org.asdfformat.asdf.AsdfFile;
import org.asdfformat.asdf.io.compression.testing.CommonsCompressReferenceFileType;
import org.asdfformat.asdf.ndarray.DoubleNdArray;
import org.asdfformat.asdf.standard.AsdfStandardType;
import org.asdfformat.asdf.testing.ReferenceFileUtils;
import org.junit.jupiter.api.Tag;
import org.junitpioneer.jupiter.cartesian.CartesianTest;

import java.io.IOException;
import java.nio.file.Path;

import static org.asdfformat.asdf.io.compression.testing.TestCategories.REFERENCE_TESTS;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Tag(REFERENCE_TESTS)
public class Lz4CompressorReferenceTest {
    @CartesianTest
    public void test1d(
            @CartesianTest.Enum(value = CommonsCompressReferenceFileType.class, names = {"NDARRAY_COMPRESSED_LZ4"}) final CommonsCompressReferenceFileType referenceFileType,
            @CartesianTest.Enum(AsdfStandardType.class) final AsdfStandardType asdfStandardType
    ) throws IOException {
        final Path path = ReferenceFileUtils.getPath(referenceFileType, asdfStandardType.getVersion());

        try (final AsdfFile asdfFile = Asdf.open(path)) {
            final DoubleNdArray doubleNdArray = asdfFile.getTree().get("arr").asNdArray().asDoubleNdArray();

            for (int i = 0; i < 10; i++) {
                assertEquals(doubleNdArray.get(i), i);
            }
        }
    }
}
