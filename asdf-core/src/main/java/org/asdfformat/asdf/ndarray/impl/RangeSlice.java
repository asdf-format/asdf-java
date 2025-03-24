package org.asdfformat.asdf.ndarray.impl;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.ToString;
import org.asdfformat.asdf.ndarray.Slice;

@ToString
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class RangeSlice implements Slice {
    private final int startIndexInclusive;
    private final Integer endIndexExclusive;
    private final int step;

    public static RangeSlice of(final int startIndexInclusive, final Integer endIndexExclusive, final int step) {
        if (step < 1) {
            throw new IllegalArgumentException("Cannot slice with step < 1");
        }

        return new RangeSlice(startIndexInclusive, endIndexExclusive, step);
    }

    public static RangeSlice of(final int startIndexInclusive, final Integer endIndexExclusive) {
        return of(startIndexInclusive, endIndexExclusive, 1);
    }

    @Override
    public void validate(final int originalLength) {
        final int resolvedStartIndexInclusive = resolveIndex(originalLength, startIndexInclusive);
        final int resolvedEndIndexInclusive = resolveIndex(originalLength, endIndexExclusive);

        if (resolvedStartIndexInclusive < 0 || resolvedStartIndexInclusive >= originalLength || resolvedEndIndexInclusive < 0 || resolvedEndIndexInclusive > originalLength) {
            throw new IllegalArgumentException(
                    String.format(
                            "%s out of range for dimension of length %d",
                            this,
                            originalLength
                    )
            );
        }

        if (resolvedEndIndexInclusive <= resolvedStartIndexInclusive) {
            throw new IllegalArgumentException(
                    String.format(
                            "%s is empty interval for dimension of length %d",
                            this,
                            originalLength
                    )
            );
        }
    }

    @Override
    public int computeNewOffset(final int originalLength, final int originalOffset, final int originalStride) {
        return originalOffset + resolveIndex(originalLength, startIndexInclusive) * originalStride;
    }

    @Override
    public int computeNewLength(final int originalLength) {
        return (resolveIndex(originalLength, endIndexExclusive) - resolveIndex(originalLength, startIndexInclusive)) / step;
    }

    @Override
    public int computeNewStride(final int originalStride) {
        return originalStride * step;
    }

    private int resolveIndex(final int length, final Integer index) {
        if (index == null) {
            return length;
        }

        if (index >= 0) {
            return index;
        }

        return length + index;
    }
}
