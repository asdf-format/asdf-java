package org.asdfformat.asdf.ndarray.impl;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Float16UtilsTest {
    @Test
    public void testPositiveZero() {
        assertEquals(0.0f, Float16Utils.float16ToFloat((short) 0x0000));
        assertEquals(
                Float.floatToIntBits(0.0f),
                Float.floatToIntBits(Float16Utils.float16ToFloat((short) 0x0000))
        );
    }

    @Test
    public void testNegativeZero() {
        assertEquals(-0.0f, Float16Utils.float16ToFloat((short) 0x8000));
        assertEquals(
                Float.floatToIntBits(-0.0f),
                Float.floatToIntBits(Float16Utils.float16ToFloat((short) 0x8000))
        );
    }

    @Test
    public void testOne() {
        assertEquals(1.0f, Float16Utils.float16ToFloat((short) 0x3C00));
    }

    @Test
    public void testTwo() {
        assertEquals(2.0f, Float16Utils.float16ToFloat((short) 0x4000));
    }

    @Test
    public void testNegativeOne() {
        assertEquals(-1.0f, Float16Utils.float16ToFloat((short) 0xBC00));
    }

    @Test
    public void testSmallestPositiveSubnormal() {
        final float result = Float16Utils.float16ToFloat((short) 0x0001);
        assertTrue(result > 0.0f);
        assertEquals(5.9604645E-8f, result);
    }

    @Test
    public void testLargestSubnormal() {
        final float result = Float16Utils.float16ToFloat((short) 0x03FF);
        assertTrue(result > 0.0f);
        assertEquals(6.097555E-5f, result);
    }

    @Test
    public void testSmallestPositiveNormal() {
        final float result = Float16Utils.float16ToFloat((short) 0x0400);
        assertTrue(result > 0.0f);
        assertEquals(6.1035156E-5f, result);
    }

    @Test
    public void testMaxFiniteValue() {
        assertEquals(65504.0f, Float16Utils.float16ToFloat((short) 0x7BFF));
    }

    @Test
    public void testNegativeMaxFiniteValue() {
        assertEquals(-65504.0f, Float16Utils.float16ToFloat((short) 0xFBFF));
    }

    @Test
    public void testPositiveInfinity() {
        assertEquals(Float.POSITIVE_INFINITY, Float16Utils.float16ToFloat((short) 0x7C00));
    }

    @Test
    public void testNegativeInfinity() {
        assertEquals(Float.NEGATIVE_INFINITY, Float16Utils.float16ToFloat((short) 0xFC00));
    }

    @Test
    public void testNaN() {
        assertTrue(Float.isNaN(Float16Utils.float16ToFloat((short) 0x7E00)));
        assertEquals(0x7FC00000, Float.floatToRawIntBits(Float16Utils.float16ToFloat((short) 0x7E00)));
    }

    @Test
    public void testSmallestNaN() {
        final float result = Float16Utils.float16ToFloat((short) 0x7C01);
        assertTrue(Float.isNaN(result));
        assertEquals(0x7F800000 | (0x001 << 13), Float.floatToRawIntBits(result));
    }

    @Test
    public void testNegativeNaN() {
        final float result = Float16Utils.float16ToFloat((short) 0xFE00);
        assertTrue(Float.isNaN(result));
        assertTrue(Float.floatToRawIntBits(result) < 0);
    }

    @Test
    public void testFloatToFloat16RoundTrip() {
        final short[] patterns = {
                (short) 0x0000, (short) 0x8000,
                (short) 0x3C00, (short) 0xBC00,
                (short) 0x4000,
                (short) 0x0001, (short) 0x03FF, (short) 0x0400,
                (short) 0x7BFF, (short) 0xFBFF,
                (short) 0x7C00, (short) 0xFC00,
        };

        for (final short pattern : patterns) {
            final float value = Float16Utils.float16ToFloat(pattern);
            if (!Float.isNaN(value)) {
                assertEquals(pattern, Float16Utils.floatToFloat16(value),
                        String.format("Round-trip failed for pattern 0x%04X", pattern & 0xFFFF));
            }
        }
    }

    @Test
    public void testFloatToFloat16NaN() {
        final short result = Float16Utils.floatToFloat16(Float.NaN);
        final int bits = result & 0xFFFF;
        assertEquals(0x1F, (bits >>> 10) & 0x1F);
        assertTrue((bits & 0x3FF) != 0);
    }

    @Test
    public void testFloatToFloat16Overflow() {
        assertEquals((short) 0x7C00, Float16Utils.floatToFloat16(Float.MAX_VALUE));
        assertEquals((short) 0x7C00, Float16Utils.floatToFloat16(100000.0f));
    }

    @Test
    public void testFloatToFloat16NegativeOverflow() {
        assertEquals((short) 0xFC00, Float16Utils.floatToFloat16(-Float.MAX_VALUE));
    }

    @Test
    public void testFloatToFloat16Underflow() {
        assertEquals((short) 0x0000, Float16Utils.floatToFloat16(1.0E-10f));
    }

    @Test
    public void testFloatToFloat16Infinities() {
        assertEquals((short) 0x7C00, Float16Utils.floatToFloat16(Float.POSITIVE_INFINITY));
        assertEquals((short) 0xFC00, Float16Utils.floatToFloat16(Float.NEGATIVE_INFINITY));
    }

    @Test
    public void testFloatToFloat16Subnormal() {
        final short result = Float16Utils.floatToFloat16(5.9604645E-8f);
        assertEquals((short) 0x0001, result);
    }

    @Test
    public void testOneThirdApproximation() {
        final float result = Float16Utils.float16ToFloat((short) 0x3555);
        assertEquals(0.33325195f, result);
    }

    @Test
    public void testRoundToNearestEvenTieRoundsDown() {
        // 1.0 in float16 = 0x3C00, next representable = 1.0009765625 = 0x3C01
        // Exact midpoint between 1.0 and 1.0009765625: 1.00048828125
        // 1.0 has even mantissa (0), so tie rounds down to 1.0
        assertEquals((short) 0x3C00, Float16Utils.floatToFloat16(1.00048828125f));
    }

    @Test
    public void testRoundToNearestEvenTieRoundsUp() {
        // 1.0009765625 in float16 = 0x3C01 (odd mantissa), next = 1.001953125 = 0x3C02
        // Exact midpoint: 1.0014648437500
        // 0x3C01 has odd mantissa (1), so tie rounds up to 0x3C02
        assertEquals((short) 0x3C02, Float16Utils.floatToFloat16(1.0014648437500f));
    }

    @Test
    public void testRoundUpWhenAboveMidpoint() {
        // Just above the midpoint between 1.0 and 1.0009765625 should round up
        assertEquals((short) 0x3C01, Float16Utils.floatToFloat16(1.0005f));
    }

    @Test
    public void testRoundDownWhenBelowMidpoint() {
        // Just below the midpoint between 1.0 and 1.0009765625 should round down
        assertEquals((short) 0x3C00, Float16Utils.floatToFloat16(1.0004f));
    }

    @Test
    public void testFloatToFloat16RoundingOverflowToInfinity() {
        // halfExponent=30, mantissa rounds up from 0x3FF to 0x400, carrying into exponent → infinity
        assertEquals((short) 0x7C00, Float16Utils.floatToFloat16(65520.0f));
        assertEquals((short) 0xFC00, Float16Utils.floatToFloat16(-65520.0f));
    }

    @Test
    public void testFloatToFloat16Float32Subnormal() {
        assertEquals((short) 0x0000, Float16Utils.floatToFloat16(Float.MIN_VALUE));
        assertEquals((short) 0x8000, Float16Utils.floatToFloat16(-Float.MIN_VALUE));
    }

    @Test
    public void testFloatToFloat16NegativeSubnormal() {
        assertEquals((short) 0x8001, Float16Utils.floatToFloat16(-5.9604645E-8f));
    }
}
