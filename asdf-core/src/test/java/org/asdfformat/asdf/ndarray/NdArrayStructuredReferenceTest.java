package org.asdfformat.asdf.ndarray;

import org.asdfformat.asdf.Asdf;
import org.asdfformat.asdf.AsdfFile;
import org.asdfformat.asdf.standard.AsdfStandardType;
import org.asdfformat.asdf.testing.CoreReferenceFileType;
import org.asdfformat.asdf.testing.ReferenceFileUtils;
import org.junit.jupiter.api.Tag;
import org.junitpioneer.jupiter.cartesian.CartesianTest;

import java.io.IOException;
import java.nio.file.Path;

import static org.asdfformat.asdf.testing.TestCategories.REFERENCE_TESTS;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Tag(REFERENCE_TESTS)
public class NdArrayStructuredReferenceTest {
    @CartesianTest
    public void test1d(
            @CartesianTest.Enum(value = CoreReferenceFileType.class, names = {"NDARRAY_STRUCTURED_1D_BLOCK"}) final CoreReferenceFileType coreTestFileType,
            @CartesianTest.Enum(AsdfStandardType.class) final AsdfStandardType asdfStandardType
    ) throws IOException {
        final Path path = ReferenceFileUtils.getPath(coreTestFileType, asdfStandardType.getVersion());

        try (final AsdfFile asdfFile = Asdf.open(path)) {
            final TupleNdArray tupleNdArray = asdfFile.getTree().getNdArray("arr").asTupleNdArray();

            assertEquals("gumbo", tupleNdArray.get(0).getString("string-field"));
            assertEquals("gumbo", tupleNdArray.get(0).getString(0));

            assertEquals(9, tupleNdArray.get(0).getInt("integer-field"));
            assertEquals(9, tupleNdArray.get(0).getInt(1));

            assertEquals(81.0, tupleNdArray.get(0).getFloat("float-field"));
            assertEquals(81.0, tupleNdArray.get(0).getFloat(2));

            assertEquals(true, tupleNdArray.get(0).getBoolean("boolean-field"));
            assertEquals(true, tupleNdArray.get(0).getBoolean(3));

            assertEquals("succotash", tupleNdArray.get(1).getString("string-field"));
            assertEquals("succotash", tupleNdArray.get(1).getString(0));

            assertEquals(-15, tupleNdArray.get(1).getInt("integer-field"));
            assertEquals(-15, tupleNdArray.get(1).getInt(1));

            assertEquals(15.5, tupleNdArray.get(1).getFloat("float-field"));
            assertEquals(15.5, tupleNdArray.get(1).getFloat(2));

            assertEquals(false, tupleNdArray.get(1).getBoolean("boolean-field"));
            assertEquals(false, tupleNdArray.get(1).getBoolean(3));
        }
    }
}
