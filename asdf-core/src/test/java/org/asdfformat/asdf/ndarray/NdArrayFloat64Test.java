package org.asdfformat.asdf.ndarray;

import org.asdfformat.asdf.Asdf;
import org.asdfformat.asdf.AsdfFile;
import org.asdfformat.asdf.standard.AsdfStandardType;
import org.asdfformat.asdf.testing.TestConstants;
import org.asdfformat.asdf.testing.TestFileType;
import org.asdfformat.asdf.testing.TestFiles;
import org.asdfformat.asdf.util.Version;
import org.junit.jupiter.api.Tag;
import org.junitpioneer.jupiter.cartesian.CartesianTest;

import java.io.IOException;
import java.nio.file.Path;

import static org.asdfformat.asdf.testing.TestCategories.INTEGRATION_TEST;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Tag(INTEGRATION_TEST)
public class NdArrayFloat64Test {
    @CartesianTest
    public void test1d(
            @CartesianTest.Enum(value = TestFileType.class, names = {"NDARRAY_FLOAT64_1D_BLOCK_BIG", "NDARRAY_FLOAT64_1D_BLOCK_LITTLE", "NDARRAY_FLOAT64_1D_INLINE"}) final TestFileType testFileType,
            @CartesianTest.Enum(AsdfStandardType.class) final AsdfStandardType asdfStandardType
    ) throws IOException {
        final Path path = TestFiles.getPath(testFileType, asdfStandardType.getVersion());

        try (final AsdfFile asdfFile = Asdf.open(path)) {
            final DoubleNdArray doubleNdArray = asdfFile.getTree().get("arr").asNdArray().asDoubleNdArray();

            assertEquals(doubleNdArray.get(0), -Double.MAX_VALUE);
            assertEquals(doubleNdArray.get(1), Double.MAX_VALUE);
            assertEquals(doubleNdArray.get(2), Double.MIN_VALUE);
            assertEquals(doubleNdArray.get(3), 0.0);
            assertEquals(doubleNdArray.get(4), Double.NaN);
            assertEquals(doubleNdArray.get(5), Double.POSITIVE_INFINITY);
            assertEquals(doubleNdArray.get(6), Double.NEGATIVE_INFINITY);
            assertEquals(doubleNdArray.get(7), 3.14159265358979323846264338327950288419716939937510582097494459230781640628620899);
            assertEquals(doubleNdArray.get(8), -3.14159265358979323846264338327950288419716939937510582097494459230781640628620899);
        }
    }
}
