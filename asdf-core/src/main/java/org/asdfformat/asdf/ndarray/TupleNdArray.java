package org.asdfformat.asdf.ndarray;

import java.util.List;

public interface TupleNdArray extends NdArray<TupleNdArray> {
    Tuple get(int... indices);

    List<String> getFieldNames();

    NdArray<?> getField(final String name);

    NdArray<?> getField(final int index);
}
