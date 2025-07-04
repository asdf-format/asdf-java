package org.asdfformat.asdf.standard.impl;

import lombok.RequiredArgsConstructor;
import org.asdfformat.asdf.io.Block;
import org.asdfformat.asdf.io.LowLevelFormat;
import org.asdfformat.asdf.io.impl.InlineBlockV1_0_0;
import org.asdfformat.asdf.ndarray.DataType;
import org.asdfformat.asdf.ndarray.DataTypeFamilyType;
import org.asdfformat.asdf.ndarray.DataTypes;
import org.asdfformat.asdf.ndarray.NdArray;
import org.asdfformat.asdf.ndarray.impl.DataTypeFieldImpl;
import org.asdfformat.asdf.ndarray.impl.NdArrayImpl;
import org.asdfformat.asdf.ndarray.impl.StringDataTypeImpl;
import org.asdfformat.asdf.ndarray.impl.TupleDataTypeImpl;
import org.asdfformat.asdf.node.AsdfNode;
import org.asdfformat.asdf.node.impl.NdArrayAsdfNode;

import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@RequiredArgsConstructor
public class NdArrayHandler_1_x implements NdArrayHandler {
    private static final Map<String, DataType> SIMPLE_DATA_TYPES = new HashMap<>();
    static {
        SIMPLE_DATA_TYPES.put("int8", DataTypes.INT8);
        SIMPLE_DATA_TYPES.put("int16", DataTypes.INT16);
        SIMPLE_DATA_TYPES.put("int32", DataTypes.INT32);
        SIMPLE_DATA_TYPES.put("int64", DataTypes.INT64);
        SIMPLE_DATA_TYPES.put("uint8", DataTypes.UINT8);
        SIMPLE_DATA_TYPES.put("uint16", DataTypes.UINT16);
        SIMPLE_DATA_TYPES.put("uint32", DataTypes.UINT32);
        SIMPLE_DATA_TYPES.put("float32", DataTypes.FLOAT32);
        SIMPLE_DATA_TYPES.put("float64", DataTypes.FLOAT64);
        SIMPLE_DATA_TYPES.put("complex64", DataTypes.COMPLEX64);
        SIMPLE_DATA_TYPES.put("complex128", DataTypes.COMPLEX128);
        SIMPLE_DATA_TYPES.put("bool8", DataTypes.BOOL8);
    }

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

        if (!node.get("source").isNumber()) {
            throw new RuntimeException("Support for ndarray with external array source is not implemented yet");
        }

        final int source = node.getInt("source");

        final int[] shape = createShape(node.get("shape"));

        final ByteOrder byteOrder = parseByteOrder(node.get("byteorder").asString());

        final DataType dataType = parseDataType(node.get("datatype"), byteOrder);

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
        final ByteOrder byteOrder = ByteOrder.BIG_ENDIAN;

        if (!node.containsKey("datatype")) {
            throw new RuntimeException("Support for implicit inline ndarray datatype not implemented yet");
        }

        final DataType dataType = parseDataType(node.get("datatype"), byteOrder);

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
                byteOrder,
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

    private ByteOrder parseByteOrder(final String value) {
        if (value.equals("big")) {
            return ByteOrder.BIG_ENDIAN;
        } else if (value.equals("little")) {
            return ByteOrder.LITTLE_ENDIAN;
        } else {
            throw new RuntimeException("Unhandled ndarray byte order: " + value);
        }
    }

    private DataType parseDataType(final AsdfNode node, final ByteOrder defaultByteOrder) {
        if (node.isString()) {
            return parseSimpleDataType(node);
        }

        if (!node.isSequence()) {
            throw new RuntimeException("Unexpected ASDF node type in dataType field: " + node);
        }

        if (node.size() == 2 && node.get(0).isString() && node.get(1).isNumber()) {
            return parseVariableLengthDataType(node);
        }

        return parseTupleDataType(node, defaultByteOrder);
    }

    private DataType parseSimpleDataType(final AsdfNode node) {
        return Optional.ofNullable(SIMPLE_DATA_TYPES.get(node.asString()))
                .orElseThrow(() -> new RuntimeException("Unrecognized data type: " + node.asString()));
    }

    private DataType parseVariableLengthDataType(final AsdfNode node) {
        final String dataTypeName = node.get(0).asString();
        if (dataTypeName.equals("ucs4")) {
            return new StringDataTypeImpl(
                    DataTypeFamilyType.UCS4,
                    node.get(1).asInt() * 4
            );
        } else if (dataTypeName.equals("ascii")) {
            return new StringDataTypeImpl(
                    DataTypeFamilyType.ASCII,
                    node.get(1).asInt()
            );
        } else {
            throw new RuntimeException("Unexpected variable-length data type: " + dataTypeName);
        }
    }

    private DataType parseTupleDataType(final AsdfNode node, final ByteOrder defaultByteOrder) {
        final List<DataType.Field> fields = new ArrayList<>();
        for (final AsdfNode childNode : node) {
            if (!childNode.isMapping()) {
                throw new RuntimeException("Unexpected child node in tuple data type: " + childNode);
            }

            final ByteOrder byteOrder;
            if (childNode.containsKey("byteorder")) {
                byteOrder = parseByteOrder(childNode.get("byteorder").asString());
            } else {
                byteOrder = defaultByteOrder;
            }

            final DataType dataType = parseDataType(childNode.get("datatype"), byteOrder);

            final String name;
            if (childNode.containsKey("name")) {
                name = childNode.getString("name");
            } else {
                name = null;
            }

            fields.add(new DataTypeFieldImpl(name, dataType, byteOrder));
        }

        return new TupleDataTypeImpl(fields);
    }
}
