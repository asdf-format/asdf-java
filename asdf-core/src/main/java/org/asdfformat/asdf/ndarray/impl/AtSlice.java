package org.asdfformat.asdf.ndarray.impl;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.ToString;
import org.asdfformat.asdf.ndarray.Slice;

@ToString
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class AtSlice implements Slice {
    public static AtSlice of(final int index) {
        if (index < 0) {
            throw new IllegalArgumentException("Cannot slice at index < 0");
        }

        return new AtSlice(index);
    }

    private final int index;

    @Override
    public void validate(final int originalLength) {
        if (index >= originalLength) {
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
        return originalOffset + index * originalStride;
    }

    @Override
    public int computeNewLength(final int originalLength) {
        return 0;
    }

    @Override
    public int computeNewStride(final int originalStride) {
        return originalStride;
    }
}
