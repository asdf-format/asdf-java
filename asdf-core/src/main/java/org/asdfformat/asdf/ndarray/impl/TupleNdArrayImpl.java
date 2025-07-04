package org.asdfformat.asdf.ndarray.impl;

import org.asdfformat.asdf.io.Block;
import org.asdfformat.asdf.ndarray.DataType;
import org.asdfformat.asdf.ndarray.NdArray;
import org.asdfformat.asdf.ndarray.Slice;
import org.asdfformat.asdf.ndarray.Slices;
import org.asdfformat.asdf.ndarray.Tuple;
import org.asdfformat.asdf.ndarray.TupleNdArray;

import java.nio.ByteOrder;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TupleNdArrayImpl extends NdArrayBase<TupleNdArray> implements TupleNdArray {
    private final Map<String, Integer> fieldToIndex;

    public TupleNdArrayImpl(final DataType dataType, final int[] shape, final ByteOrder byteOrder, final int[] strides, final int offset, final Block block) {
        super(dataType, shape, byteOrder, strides, offset, block);

        fieldToIndex = IntStream.range(0, dataType.getFields().size())
                .boxed()
                .collect(Collectors.toMap(i -> dataType.getFields().get(i).getName(), Function.identity()));
    }

    @Override
    protected NdArray<TupleNdArray> newInstance(final DataType dataType, final int[] shape, final ByteOrder byteOrder, final int[] strides, final int offset, final Block block) {
        return new TupleNdArrayImpl(dataType, shape, byteOrder, strides, offset, block);
    }

    @Override
    protected String getClassName() {
        return "TupleNdArray";
    }

    @Override
    public Tuple get(final int... indices) {
        final Slice[] slices = IntStream.of(indices).mapToObj(Slices::at).toArray(Slice[]::new);
        return new TupleImpl((TupleNdArray)slice(slices));
    }

    @Override
    public <ARRAY> ARRAY toArray(final ARRAY array) {
        final ArraySetter<Tuple[]> setter = (byteBuffer, arr, index, length) -> {
            for (int i = index; i < length; i++) {
                final TupleNdArray singleElementView = new TupleNdArrayImpl(
                        dataType,
                        new int[] {1},
                        byteOrder,
                        new int[] {dataType.getWidthBytes()},
                        byteBuffer.position(),
                        block
                );
                arr[i] = new TupleImpl(singleElementView);
                byteBuffer.position(byteBuffer.position() + dataType.getWidthBytes());
            }
        };

        return toArray(array, Tuple.class, setter);
    }

    @Override
    public List<String> getFieldNames() {
        return dataType.getFields().stream().map(DataType.Field::getName).collect(Collectors.toList());
    }

    @Override
    public NdArray<?> getField(final String name) {
        final int index = Optional.ofNullable(fieldToIndex.get(name))
                .orElseThrow(() -> new IllegalArgumentException("Unrecognized field name: " + name));

        return getField(index);
    }

    @Override
    public NdArray<?> getField(final int index) {
        if (index < 0 || index >= dataType.getFields().size()) {
            throw new IllegalArgumentException("Index " + index + " is out of bounds");
        }

        final DataType.Field field = dataType.getFields().get(index);

        int tupleOffset = 0;
        for (int i = 0; i < index; i++) {
            tupleOffset += dataType.getFields().get(i).getDataType().getWidthBytes();
        }

        return new NdArrayImpl(
                field.getDataType(),
                shape,
                field.getByteOrder(),
                strides,
                offset + tupleOffset,
                block
        );
    }
}
