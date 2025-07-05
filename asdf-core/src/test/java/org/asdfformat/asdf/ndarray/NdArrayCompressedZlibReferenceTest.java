package org.asdfformat.asdf.ndarray;

import org.asdfformat.asdf.Asdf;
import org.asdfformat.asdf.AsdfFile;
import org.asdfformat.asdf.standard.AsdfStandardType;
import org.asdfformat.asdf.testing.TestFileType;
import org.asdfformat.asdf.testing.TestFiles;
import org.junit.jupiter.api.Tag;
import org.junitpioneer.jupiter.cartesian.CartesianTest;

import java.io.IOException;
import java.nio.file.Path;

import static org.asdfformat.asdf.testing.TestCategories.REFERENCE_TESTS;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Tag(REFERENCE_TESTS)
public class NdArrayCompressedZlibReferenceTest {
    @CartesianTest
    public void test1d(
            @CartesianTest.Enum(value = TestFileType.class, names = {"NDARRAY_COMPRESSED_ZLIB"}) final TestFileType testFileType,
            @CartesianTest.Enum(AsdfStandardType.class) final AsdfStandardType asdfStandardType
    ) throws IOException {
        final Path path = TestFiles.getPath(testFileType, asdfStandardType.getVersion());

        try (final AsdfFile asdfFile = Asdf.open(path)) {
            final DoubleNdArray doubleNdArray = asdfFile.getTree().get("arr").asNdArray().asDoubleNdArray();

            for (int i = 0; i < 10; i++) {
                assertEquals(doubleNdArray.get(i), i);
            }
        }
    }
}
