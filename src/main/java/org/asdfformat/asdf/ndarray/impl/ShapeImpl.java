package org.asdfformat.asdf.ndarray.impl;

import org.asdfformat.asdf.ndarray.Shape;

public class ShapeImpl implements Shape {
    private final int[] shape;

    public ShapeImpl(final int[] shape) {
        this.shape = shape;
    }

    @Override
    public int getRank() {
        return shape.length;
    }

    @Override
    public int get(final int i) {
        if (i < 0 || i >= shape.length) {
            throw new IllegalArgumentException(String.format("Index %d is out of range for a shape with rank %d", i, shape.length));
        }

        return shape[i];
    }
}
