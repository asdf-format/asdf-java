package org.asdfformat.asdf.ndarray.impl;

public class Float16Utils {
    public static float float16ToFloat(final short bits) {
        final int halfBits = bits & 0xFFFF;
        final int sign = (halfBits >>> 15) & 0x1;
        final int exponent = (halfBits >>> 10) & 0x1F;
        final int mantissa = halfBits & 0x3FF;

        final int floatBits;
        if (exponent == 0) {
            if (mantissa == 0) {
                floatBits = sign << 31;
            } else {
                // Subnormal: normalize by shifting mantissa until the leading 1 is in bit 10
                int m = mantissa;
                int e = -14 + 127;
                while ((m & 0x400) == 0) {
                    m <<= 1;
                    e--;
                }
                m &= 0x3FF;
                floatBits = (sign << 31) | (e << 23) | (m << 13);
            }
        } else if (exponent == 31) {
            // Inf or NaN: rebased exponent to float32's 255
            floatBits = (sign << 31) | (0xFF << 23) | (mantissa << 13);
        } else {
            // Normal: rebase exponent from bias-15 to bias-127
            final int floatExponent = exponent - 15 + 127;
            floatBits = (sign << 31) | (floatExponent << 23) | (mantissa << 13);
        }

        return Float.intBitsToFloat(floatBits);
    }

    public static short floatToFloat16(final float value) {
        final int floatBits = Float.floatToIntBits(value);
        final int sign = (floatBits >>> 31) & 0x1;
        final int exponent = (floatBits >>> 23) & 0xFF;
        final int mantissa = floatBits & 0x7FFFFF;

        final int halfBits;
        if (exponent == 0) {
            halfBits = sign << 15;
        } else if (exponent == 0xFF) {
            if (mantissa == 0) {
                halfBits = (sign << 15) | (0x1F << 10);
            } else {
                final int halfMantissa = mantissa >>> 13;
                halfBits = (sign << 15) | (0x1F << 10) | (halfMantissa != 0 ? halfMantissa : 0x1);
            }
        } else {
            final int halfExponent = exponent - 127 + 15;
            if (halfExponent >= 31) {
                halfBits = (sign << 15) | (0x1F << 10);
            } else if (halfExponent <= 0) {
                if (halfExponent < -10) {
                    halfBits = sign << 15;
                } else {
                    final int shift = 1 - halfExponent + 13;
                    final int m = (mantissa | 0x800000) >>> shift;
                    final int roundBit = ((mantissa | 0x800000) >>> (shift - 1)) & 0x1;
                    final int stickyBit = ((mantissa | 0x800000) & ((1 << (shift - 1)) - 1)) != 0 ? 1 : 0;
                    final int rounded = m + (roundBit & (stickyBit | (m & 1)));
                    halfBits = (sign << 15) | rounded;
                }
            } else {
                final int truncated = (mantissa >>> 13);
                final int roundBit = (mantissa >>> 12) & 0x1;
                final int stickyBit = (mantissa & 0xFFF) != 0 ? 1 : 0;
                final int rounded = truncated + (roundBit & (stickyBit | (truncated & 1)));
                halfBits = (sign << 15) | ((halfExponent << 10) + rounded);
            }
        }

        return (short) halfBits;
    }

    private Float16Utils() {}
}
