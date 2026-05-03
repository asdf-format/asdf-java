package org.asdfformat.asdf.ndarray;

import org.asdfformat.asdf.Asdf;
import org.asdfformat.asdf.AsdfFile;
import org.asdfformat.asdf.standard.AsdfStandardType;
import org.asdfformat.asdf.testing.CoreReferenceFileType;
import org.asdfformat.asdf.testing.ReferenceFileUtils;
import org.asdfformat.asdf.util.Version;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.stream.Stream;

import static org.asdfformat.asdf.testing.TestCategories.REFERENCE_TESTS;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Tag(REFERENCE_TESTS)
public class NdArrayFloat16ReferenceTest {
    private static final Version FLOAT16_MIN_VERSION = new Version(1, 6, 0);

    private static final CoreReferenceFileType[] FILE_TYPES = {
            CoreReferenceFileType.NDARRAY_FLOAT16_1D_BLOCK_BIG,
            CoreReferenceFileType.NDARRAY_FLOAT16_1D_BLOCK_LITTLE,
            CoreReferenceFileType.NDARRAY_FLOAT16_1D_INLINE,
    };

    private static Stream<Arguments> float16Args() {
        return Arrays.stream(FILE_TYPES)
                .flatMap(fileType -> Arrays.stream(AsdfStandardType.values())
                        .filter(std -> std.getVersion().compareTo(FLOAT16_MIN_VERSION) >= 0)
                        .map(std -> Arguments.of(fileType, std)));
    }

    @ParameterizedTest
    @MethodSource("float16Args")
    public void testFloat1d(final CoreReferenceFileType coreTestFileType, final AsdfStandardType asdfStandardType) throws IOException {
        final Path path = ReferenceFileUtils.getPath(coreTestFileType, asdfStandardType.getVersion());

        try (final AsdfFile asdfFile = Asdf.open(path)) {
            final FloatNdArray floatNdArray = asdfFile.getTree().get("arr").asNdArray().asFloatNdArray();

            assertEquals(-65504.0f, floatNdArray.get(0));
            assertEquals(65504.0f, floatNdArray.get(1));
            assertEquals(5.9604645E-8f, floatNdArray.get(2));
            assertEquals(0.0f, floatNdArray.get(3));
            assertTrue(Float.isNaN(floatNdArray.get(4)));
            assertEquals(Float.POSITIVE_INFINITY, floatNdArray.get(5));
            assertEquals(Float.NEGATIVE_INFINITY, floatNdArray.get(6));
            assertEquals(3.140625f, floatNdArray.get(7));
            assertEquals(-3.140625f, floatNdArray.get(8));

            final float[] arr = floatNdArray.toArray(new float[9]);
            assertEquals(-65504.0f, arr[0]);
            assertEquals(65504.0f, arr[1]);
            assertEquals(5.9604645E-8f, arr[2]);
            assertEquals(0.0f, arr[3]);
            assertTrue(Float.isNaN(arr[4]));
            assertEquals(Float.POSITIVE_INFINITY, arr[5]);
            assertEquals(Float.NEGATIVE_INFINITY, arr[6]);
            assertEquals(3.140625f, arr[7]);
            assertEquals(-3.140625f, arr[8]);
        }
    }

    @ParameterizedTest
    @MethodSource("float16Args")
    public void testDouble1d(final CoreReferenceFileType coreTestFileType, final AsdfStandardType asdfStandardType) throws IOException {
        final Path path = ReferenceFileUtils.getPath(coreTestFileType, asdfStandardType.getVersion());

        try (final AsdfFile asdfFile = Asdf.open(path)) {
            final DoubleNdArray doubleNdArray = asdfFile.getTree().get("arr").asNdArray().asDoubleNdArray();

            assertEquals(-65504.0, doubleNdArray.get(0));
            assertEquals(65504.0, doubleNdArray.get(1));
            assertEquals(5.960464477539063E-8, doubleNdArray.get(2));
            assertEquals(0.0, doubleNdArray.get(3));
            assertTrue(Double.isNaN(doubleNdArray.get(4)));
            assertEquals(Double.POSITIVE_INFINITY, doubleNdArray.get(5));
            assertEquals(Double.NEGATIVE_INFINITY, doubleNdArray.get(6));
            assertEquals(3.140625, doubleNdArray.get(7));
            assertEquals(-3.140625, doubleNdArray.get(8));

            final double[] arr = doubleNdArray.toArray(new double[9]);
            assertEquals(-65504.0, arr[0]);
            assertEquals(65504.0, arr[1]);
            assertEquals(5.960464477539063E-8, arr[2]);
            assertEquals(0.0, arr[3]);
            assertTrue(Double.isNaN(arr[4]));
            assertEquals(Double.POSITIVE_INFINITY, arr[5]);
            assertEquals(Double.NEGATIVE_INFINITY, arr[6]);
            assertEquals(3.140625, arr[7]);
            assertEquals(-3.140625, arr[8]);
        }
    }

    @ParameterizedTest
    @MethodSource("float16Args")
    public void testBigDecimal1d(final CoreReferenceFileType coreTestFileType, final AsdfStandardType asdfStandardType) throws IOException {
        final Path path = ReferenceFileUtils.getPath(coreTestFileType, asdfStandardType.getVersion());

        try (final AsdfFile asdfFile = Asdf.open(path)) {
            final BigDecimalNdArray bigDecimalNdArray = asdfFile.getTree().get("arr").asNdArray().asBigDecimalNdArray();

            assertEquals(BigDecimal.valueOf(-65504.0), bigDecimalNdArray.get(0));
            assertEquals(BigDecimal.valueOf(65504.0), bigDecimalNdArray.get(1));
            assertEquals(BigDecimal.valueOf(5.960464477539063E-8), bigDecimalNdArray.get(2));
            assertEquals(BigDecimal.valueOf(0.0), bigDecimalNdArray.get(3));
            assertEquals(BigDecimal.valueOf(3.140625), bigDecimalNdArray.get(7));
            assertEquals(BigDecimal.valueOf(-3.140625), bigDecimalNdArray.get(8));

        }
    }
}
