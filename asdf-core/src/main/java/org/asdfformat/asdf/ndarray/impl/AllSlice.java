package org.asdfformat.asdf.ndarray.impl;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.ToString;
import org.asdfformat.asdf.ndarray.Slice;

@ToString
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class AllSlice implements Slice {
    private static final AllSlice INSTANCE = new AllSlice();

    public static AllSlice instance() {
        return INSTANCE;
    }

    @Override
    public void validate(final int originalLength) {
        // Any length will do
    }

    @Override
    public int computeNewOffset(final int originalLength, final int originalOffset, final int originalStride) {
        return originalOffset;
    }

    @Override
    public int computeNewLength(final int originalLength) {
        return originalLength;
    }

    @Override
    public int computeNewStride(final int originalStride) {
        return originalStride;
    }
}
