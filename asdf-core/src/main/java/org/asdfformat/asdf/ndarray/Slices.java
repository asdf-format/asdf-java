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
     * Select a range of a dimension, beginning at startIndexInclusive
     * and extending to the end of the array.
     * @param startIndexInclusive inclusive start index
     * @return slice
     */
    public static Slice range(int startIndexInclusive) {
        return RangeSlice.of(startIndexInclusive, null);
    }

    /**
     * Select a range of a dimension.
     * @param startIndexInclusive inclusive start index
     * @param endIndexExclusive exclusive end index (or null for the end of the array)
     * @return slice
     */
    public static Slice range(int startIndexInclusive, Integer endIndexExclusive) {
        return RangeSlice.of(startIndexInclusive, endIndexExclusive);
    }

    /**
     * Select a range of a dimension, with spacing between selected values.
     * @param startIndexInclusive inclusive start index
     * @param endIndexExclusive exclusive end index (or null for the end of the array)
     * @param step spacing between values
     * @return slice
     */
    public static Slice range(int startIndexInclusive, Integer endIndexExclusive, int step) {
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
