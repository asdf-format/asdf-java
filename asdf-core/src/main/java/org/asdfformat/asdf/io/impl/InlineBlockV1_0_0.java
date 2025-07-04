package org.asdfformat.asdf.io.impl;

import org.asdfformat.asdf.io.Block;
import org.asdfformat.asdf.ndarray.DataType;
import org.asdfformat.asdf.ndarray.DataTypeFamilyType;
import org.asdfformat.asdf.ndarray.DataTypes;
import org.asdfformat.asdf.node.AsdfNode;
import org.asdfformat.asdf.util.AsdfCharsets;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

public class InlineBlockV1_0_0 implements Block {
    private static final Map<DataType, BiConsumer<ByteBuffer, AsdfNode>> SIMPLE_VALUE_WRITERS = new HashMap<>();
    static {
        SIMPLE_VALUE_WRITERS.put(DataTypes.UINT8, (b, n) -> b.put(n.asByte()));
        SIMPLE_VALUE_WRITERS.put(DataTypes.INT8, (b, n) -> b.put(n.asByte()));
        SIMPLE_VALUE_WRITERS.put(DataTypes.UINT16, (b, n) -> b.putShort(n.asShort()));
        SIMPLE_VALUE_WRITERS.put(DataTypes.INT16, (b, n) -> b.putShort(n.asShort()));
        SIMPLE_VALUE_WRITERS.put(DataTypes.UINT32, (b, n) -> b.putInt(n.asInt()));
        SIMPLE_VALUE_WRITERS.put(DataTypes.INT32, (b, n) -> b.putInt(n.asInt()));
        SIMPLE_VALUE_WRITERS.put(DataTypes.UINT64, (b, n) -> b.putLong(n.asLong()));
        SIMPLE_VALUE_WRITERS.put(DataTypes.INT64, (b, n) -> b.putLong(n.asLong()));
        SIMPLE_VALUE_WRITERS.put(DataTypes.FLOAT32, (b, n) -> b.putFloat(n.asFloat()));
        SIMPLE_VALUE_WRITERS.put(DataTypes.FLOAT64, (b, n) -> b.putDouble(n.asDouble()));
        SIMPLE_VALUE_WRITERS.put(DataTypes.BOOL8, (b, n) -> b.put((byte)(n.asBoolean() ? 1 : 0)));
    }

    private final ByteBuffer dataBuffer;

    public InlineBlockV1_0_0(final AsdfNode dataNode, final int[] shape, final DataType dataType) {
        dataBuffer = createDataBuffer(dataNode, shape, dataType);
    }

    @Override
    public long getEndPosition() {
        throw new RuntimeException("getEndPosition() not implemented for inline block");
    }

    @Override
    public ByteBuffer getDataBuffer() {
        return dataBuffer.duplicate();
    }

    @Override
    public boolean isCompressed() {
        return false;
    }

    private ByteBuffer createDataBuffer(final AsdfNode node, final int[] shape, final DataType dataType) {
        int totalSizeBytes = dataType.getWidthBytes();
        for (final int len : shape) {
            totalSizeBytes *= len;
        }

        final ByteBuffer byteBuffer = ByteBuffer.allocate(totalSizeBytes);
        byteBuffer.order(ByteOrder.BIG_ENDIAN);

        if (SIMPLE_VALUE_WRITERS.containsKey(dataType)) {
            writeToBuffer(node, shape, byteBuffer, SIMPLE_VALUE_WRITERS.get(dataType));
            return byteBuffer;
        }

        if (dataType.getFamily() == DataTypeFamilyType.ASCII) {
            final BiConsumer<ByteBuffer, AsdfNode> valueWriter = (b, valueNode) -> {
                final byte[] value = valueNode.asString().getBytes(AsdfCharsets.ASCII);
                b.put(value);
                for (int i = value.length; i < dataType.getWidthBytes(); i++) {
                    b.put((byte)0);
                }
            };
            writeToBuffer(node, shape, byteBuffer, valueWriter);
            return byteBuffer;
        }

        if (dataType.getFamily() == DataTypeFamilyType.UCS4) {
            final BiConsumer<ByteBuffer, AsdfNode> valueWriter = (b, valueNode) -> {
                final byte[] value = valueNode.asString().getBytes(AsdfCharsets.UTF_32BE);
                b.put(value);
                for (int i = value.length; i < dataType.getWidthBytes(); i++) {
                    b.put((byte)0);
                }
            };
            writeToBuffer(node, shape, byteBuffer, valueWriter);
            return byteBuffer;
        }

        throw new RuntimeException("Unhandled inline ndarray data type: " + dataType);
    }

    private void writeToBuffer(final AsdfNode node, final int[] shape, final ByteBuffer byteBuffer, final BiConsumer<ByteBuffer, AsdfNode> valueWriter) {
        if (shape.length == 1) {
            for (final AsdfNode element : node) {
                valueWriter.accept(byteBuffer, element);
            }
        } else {
            for (final AsdfNode element : node) {
                writeToBuffer(
                        element,
                        Arrays.copyOfRange(shape, 1, shape.length),
                        byteBuffer,
                        valueWriter
                );
            }
        }
    }
}
