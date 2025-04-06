package org.asdfformat.asdf.io.impl;

import org.asdfformat.asdf.io.Block;
import org.asdfformat.asdf.ndarray.DataType;
import org.asdfformat.asdf.node.AsdfNode;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

public class InlineBlockV1_0_0 implements Block {
    private static Map<DataType, BiConsumer<ByteBuffer, AsdfNode>> VALUE_WRITERS = new HashMap<>();
    static {
        VALUE_WRITERS.put(DataType.UINT8, (b, n) -> b.put(n.asByte()));
        VALUE_WRITERS.put(DataType.INT8, (b, n) -> b.put(n.asByte()));
        VALUE_WRITERS.put(DataType.UINT16, (b, n) -> b.putShort(n.asShort()));
        VALUE_WRITERS.put(DataType.INT16, (b, n) -> b.putShort(n.asShort()));
        VALUE_WRITERS.put(DataType.UINT32, (b, n) -> b.putInt(n.asInt()));
        VALUE_WRITERS.put(DataType.INT32, (b, n) -> b.putInt(n.asInt()));
        VALUE_WRITERS.put(DataType.UINT64, (b, n) -> b.putLong(n.asLong()));
        VALUE_WRITERS.put(DataType.INT64, (b, n) -> b.putLong(n.asLong()));
        VALUE_WRITERS.put(DataType.FLOAT32, (b, n) -> b.putFloat(n.asFloat()));
        VALUE_WRITERS.put(DataType.FLOAT64, (b, n) -> b.putDouble(n.asDouble()));
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

        final BiConsumer<ByteBuffer, AsdfNode> valueWriter = VALUE_WRITERS.get(dataType);
        if (valueWriter == null) {
            throw new RuntimeException("Unhandled data type: " + dataType);
        }

        final ByteBuffer byteBuffer = ByteBuffer.allocate(totalSizeBytes);
        byteBuffer.order(ByteOrder.BIG_ENDIAN);

        writeToBuffer(node, shape, byteBuffer, valueWriter);

        return byteBuffer;
    }

    private void writeToBuffer(final AsdfNode node, final int[] shape, final ByteBuffer byteBuffer, final BiConsumer<ByteBuffer, AsdfNode> valueWriter) {
        if (shape.length == 1) {
            for (final AsdfNode element : node) {
                valueWriter.accept(byteBuffer, node);
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
