package org.asdfformat.asdf.ndarray;

/**
 * Shape of an n-dimensional array.
 */
public interface Shape {

    /**
     * Return the number of dimensions of this array's
     * shape.
     * @return number of dimensions
     */
    int getRank();

    /**
     * Get the length of a single dimension
     * @param i dimension index (zero-based)
     * @return dimension length
     */
    int get(int i);
}
