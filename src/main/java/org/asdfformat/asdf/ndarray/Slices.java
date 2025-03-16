package org.asdfformat.asdf.ndarray;

/**
 * Methods for defining n-dimensional array slices.
 */
public class Slices {

    /**
     * Select all of a dimension.
     * @return slice
     */
    public static Slice all() {
        throw new RuntimeException("Not implemented");
    }

    /**
     * Select a range of a dimension.
     * @param startIndexInclusive inclusive start index
     * @param endIndexExclusive exclusive end index
     * @return slice
     */
    public static Slice range(int startIndexInclusive, int endIndexExclusive) {
        throw new RuntimeException("Not implemented");
    }

    /**
     * Select a range of a dimension, with spacing between selected values.
     * @param startIndexInclusive inclusive start index
     * @param endIndexExclusive exclusive end index
     * @param step spacing between values
     * @return slice
     */
    public static Slice range(int startIndexInclusive, int endIndexExclusive, int step) {
        throw new RuntimeException("Not implemented");
    }

    /**
     * Select a single index of a dimension. Reduces the rank of
     * the array by 1.
     * @param index selected index
     * @return slice
     */
    public static Slice at(int index) {
        throw new RuntimeException("Not implemented");
    }
}
