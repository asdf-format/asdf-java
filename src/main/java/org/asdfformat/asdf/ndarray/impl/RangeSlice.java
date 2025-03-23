package org.asdfformat.asdf.ndarray.impl;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.ToString;
import org.asdfformat.asdf.ndarray.Slice;

@ToString
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class RangeSlice implements Slice {
    private final int startIndexInclusive;
    private final int endIndexExclusive;
    private final int step;

    public static RangeSlice of(final int startIndexInclusive, final int endIndexExclusive, final int step) {
        if (startIndexInclusive < 0) {
            throw new IllegalArgumentException("Cannot slice at start index < 0");
        }

        if (endIndexExclusive < 0) {
            throw new IllegalArgumentException("Cannot slice at end index < 0");
        }

        if (step < 1) {
            throw new IllegalArgumentException("Cannot slice with step < 1");
        }

        if (startIndexInclusive > endIndexExclusive) {
            throw new IllegalArgumentException("Cannot slice with start index > end index");
        }

        if (endIndexExclusive == startIndexInclusive) {
            throw new IllegalArgumentException("Empty slice");
        }

        return new RangeSlice(startIndexInclusive, endIndexExclusive, step);
    }

    public static RangeSlice of(final int startIndexInclusive, final int endIndexExclusive) {
        return of(startIndexInclusive, endIndexExclusive, 1);
    }

    @Override
    public void validate(final int originalLength) {
        if (startIndexInclusive >= originalLength || endIndexExclusive > originalLength) {
            throw new IllegalArgumentException(
                    String.format(
                            "%s out of range for dimension of length %d",
                            this,
                            originalLength
                    )
            );
        }
    }

    @Override
    public int computeNewOffset(final int originalOffset, final int originalStride) {
        return originalOffset + startIndexInclusive * originalStride;
    }

    @Override
    public int computeNewLength(final int originalLength) {
        return (endIndexExclusive - startIndexInclusive) / step;
    }

    @Override
    public int computeNewStride(final int originalStride) {
        return originalStride * step;
    }
}
