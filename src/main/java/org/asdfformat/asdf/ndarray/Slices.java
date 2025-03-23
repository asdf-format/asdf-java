package org.asdfformat.asdf.ndarray;

import org.asdfformat.asdf.ndarray.impl.AllSlice;
import org.asdfformat.asdf.ndarray.impl.AtSlice;
import org.asdfformat.asdf.ndarray.impl.RangeSlice;

/**
 * Methods for defining n-dimensional array slices.
 */
public class Slices {

    /**
     * Select all of a dimension.
     * @return slice
     */
    public static Slice all() {
        return AllSlice.instance();
    }

    /**
     * Select a range of a dimension.
     * @param startIndexInclusive inclusive start index
     * @param endIndexExclusive exclusive end index
     * @return slice
     */
    public static Slice range(int startIndexInclusive, int endIndexExclusive) {
        return RangeSlice.of(startIndexInclusive, endIndexExclusive);
    }

    /**
     * Select a range of a dimension, with spacing between selected values.
     * @param startIndexInclusive inclusive start index
     * @param endIndexExclusive exclusive end index
     * @param step spacing between values
     * @return slice
     */
    public static Slice range(int startIndexInclusive, int endIndexExclusive, int step) {
        return RangeSlice.of(startIndexInclusive, endIndexExclusive, step);
    }

    /**
     * Select a single index of a dimension. Reduces the rank of
     * the array by 1.
     * @param index selected index
     * @return slice
     */
    public static Slice at(int index) {
        return AtSlice.of(index);
    }
}
