package org.asdfformat.asdf.standard.impl;

import lombok.RequiredArgsConstructor;
import org.asdfformat.asdf.io.Block;
import org.asdfformat.asdf.io.LowLevelFormat;
import org.asdfformat.asdf.io.impl.InlineBlockV1_0_0;
import org.asdfformat.asdf.ndarray.DataType;
import org.asdfformat.asdf.ndarray.NdArray;
import org.asdfformat.asdf.ndarray.impl.NdArrayImpl;
import org.asdfformat.asdf.node.AsdfNode;
import org.asdfformat.asdf.node.impl.NdArrayAsdfNode;

import java.nio.ByteOrder;
import java.util.HashSet;
import java.util.Set;

@RequiredArgsConstructor
public class NdArrayHandler_1_x implements NdArrayHandler {
    private final String ndArrayTag;

    @Override
    public Set<String> getNdArrayTags() {
        final Set<String> tags = new HashSet<>();
        tags.add(ndArrayTag);
        return tags;
    }

    @Override
    public NdArray<?> createNdArray(final LowLevelFormat lowLevelFormat, final NdArrayAsdfNode node) {
        if (node.containsKey("data")) {
            return createInlineNdArray(node);
        }

        if (!node.get("datatype").isString()) {
            throw new RuntimeException("Support for ndarray with structured datatype is not implemented yet");
        }

        final DataType dataType = DataType.fromString(node.getString("datatype"));

        if (!node.get("source").isNumber()) {
            throw new RuntimeException("Support for ndarray with external array source is not implemented yet");
        }

        final int source = node.getInt("source");

        final int[] shape = createShape(node.get("shape"));

        final String byteOrderValue = node.getString("byteorder");
        final ByteOrder byteOrder;
        if (byteOrderValue.equals("big")) {
            byteOrder = ByteOrder.BIG_ENDIAN;
        } else if (byteOrderValue.equals("little")) {
            byteOrder = ByteOrder.LITTLE_ENDIAN;
        } else {
            throw new RuntimeException("Unhandled ndarray byte order: " + byteOrderValue);
        }

        final int offset;
        if (node.containsKey("offset")) {
            offset = node.getInt("offset");
        } else {
            offset = 0;
        }

        final int[] strides;
        if (node.containsKey("strides")) {
            strides = new int[shape.length];
            for (int i = 0; i < strides.length; i++) {
                strides[i] = node.get("strides").getInt(i);
            }
        } else {
            strides = createImplicitStrides(dataType, shape);
        }

        if (node.containsKey("mask")) {
            throw new RuntimeException("Support for masked ndarray not implemented yet");
        }

        return new NdArrayImpl(
                dataType,
                shape,
                byteOrder,
                strides,
                offset,
                lowLevelFormat.getBlock(source)
        );
    }

    public NdArray<?> createInlineNdArray(final NdArrayAsdfNode node) {
        if (!node.get("datatype").isString()) {
            throw new RuntimeException("Support for ndarray with structured datatype is not implemented yet");
        }

        final DataType dataType = DataType.fromString(node.getString("datatype"));

        final int[] shape = createShape(node.get("shape"));

        final int[] strides = createImplicitStrides(dataType, shape);

        if (node.containsKey("mask")) {
            throw new RuntimeException("Support for masked ndarray not implemented yet");
        }

        final Block block = new InlineBlockV1_0_0(
                node.get("data"),
                shape,
                dataType
        );

        return new NdArrayImpl(
                dataType,
                shape,
                ByteOrder.BIG_ENDIAN,
                strides,
                0,
                block
        );
    }

    private int[] createShape(final AsdfNode shapeNode) {
        final int[] shape = new int[shapeNode.size()];

        for (int i = 0; i < shape.length; i++) {
            final AsdfNode element = shapeNode.get(i);
            if (element.isString()) {
                throw new RuntimeException("Support for streaming ndarray is not implemented yet");
            }
            shape[i] = element.asInt();
        }

        return shape;
    }

    private int[] createImplicitStrides(final DataType dataType, final int[] shape) {
        final int[] strides = new int[shape.length];

        int nextStrides = dataType.getWidthBytes();
        for (int i = shape.length - 1; i >= 0; i--) {
            strides[i] = nextStrides;
            nextStrides *= shape[i];
        }

        return strides;
    }
}
