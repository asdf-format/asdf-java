package org.asdfformat.asdf.io.impl;

import java.io.ByteArrayOutputStream;
import java.io.DataInput;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.asdfformat.asdf.io.Block;
import org.asdfformat.asdf.io.LowLevelFormats;
import org.asdfformat.asdf.io.util.IOUtils;
import org.asdfformat.asdf.io.LowLevelFormat;

public class LowLevelFormatV1Impl implements LowLevelFormat {
    public static final byte[] VERSION_IDENTIFIER = " 1.0.0\n".getBytes(StandardCharsets.UTF_8);
    public static final byte[] END_OF_YAML_SEQUENCE = { 10, 46, 46, 46, 10 }; // \n...\n

    private final byte[] treeBytes;
    private final List<Block> blocks;

    public static LowLevelFormat fromFile(final RandomAccessFile file) throws IOException {
        file.seek(0);

        final byte[] treeBytes = readTree(file);
        final List<Block> blocks = readBlocks(file);

        return new LowLevelFormatV1Impl(treeBytes, blocks);
    }

    private static byte[] readTree(final DataInput input) throws IOException {
        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        final long bytesRead = IOUtils.readUntil(input, END_OF_YAML_SEQUENCE, outputStream, LowLevelFormats.MAX_TREE_LENGTH);
        if (bytesRead < 0) {
            return new byte[0];
        }
        return outputStream.toByteArray();
    }

    private static List<Block> readBlocks(final RandomAccessFile file) throws IOException {
        final List<Block> blocks = new ArrayList<>();

        if (IOUtils.seekUntil(file, BlockV1Impl.START_OF_BLOCK_SEQUENCE) < 0) {
            return blocks;
        }

        file.seek(file.getFilePointer() - BlockV1Impl.START_OF_BLOCK_SEQUENCE.length);

        final byte[] buffer = new byte[BlockV1Impl.START_OF_BLOCK_SEQUENCE.length];
        while (true) {
            final long bytesRead = file.read(buffer);
            if (bytesRead < BlockV1Impl.START_OF_BLOCK_SEQUENCE.length) {
                break;
            }

            if (!Arrays.equals(buffer, BlockV1Impl.START_OF_BLOCK_SEQUENCE)) {
                break;
            }

            final Block block = BlockV1Impl.fromFile(
                file,
                file.getFilePointer() - BlockV1Impl.START_OF_BLOCK_SEQUENCE.length
            );
            blocks.add(block);
            file.seek(block.getEndPosition());
        }

        return blocks;
    }

    public LowLevelFormatV1Impl(final byte[] treeBytes, final List<Block> blocks) {
        this.treeBytes = treeBytes;
        this.blocks = blocks;
    }

    @Override
    public byte[] getTreeBytes() {
        return treeBytes;
    }

    @Override
    public Block getBlock(final int index) {
        return blocks.get(index);
    }
}
