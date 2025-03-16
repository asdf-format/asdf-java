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
import org.asdfformat.asdf.util.Version;

public class LowLevelFormatV1_0_0 implements LowLevelFormat {
    public static final Version VERSION = new Version(1, 0, 0);

    private static final byte[] END_OF_YAML_SEQUENCE = "\n...\n".getBytes(StandardCharsets.UTF_8);
    private static final byte[] ASDF_STANDARD_VERSION_SEQUENCE = "#ASDF_STANDARD ".getBytes(StandardCharsets.UTF_8);
    private static final byte[] END_OF_VERSION = "\n".getBytes(StandardCharsets.UTF_8);

    @Override
    public Version getVersion() {
        return VERSION;
    }

    public static LowLevelFormat fromFile(final RandomAccessFile file) throws IOException {
        file.seek(0);

        if (IOUtils.seekUntil(file, ASDF_STANDARD_VERSION_SEQUENCE, 100) == -1) {
            throw new RuntimeException("Missing #ASDF_STANDARD comment");
        }

        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        IOUtils.readUntil(file, END_OF_VERSION, outputStream, 100);

        final byte[] versionBytes = outputStream.toByteArray();
        final Version asdfStandardVersion = Version.fromString(
            new String(versionBytes, 0, versionBytes.length - 1, StandardCharsets.UTF_8)
        );

        final byte[] treeBytes = readTree(file);
        final List<Block> blocks = readBlocks(file);

        return new LowLevelFormatV1_0_0(asdfStandardVersion, treeBytes, blocks);
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

        if (IOUtils.seekUntil(file, BlockV1_0_0.START_OF_BLOCK_SEQUENCE) < 0) {
            return blocks;
        }

        file.seek(file.getFilePointer() - BlockV1_0_0.START_OF_BLOCK_SEQUENCE.length);

        final byte[] buffer = new byte[BlockV1_0_0.START_OF_BLOCK_SEQUENCE.length];
        while (true) {
            final long bytesRead = file.read(buffer);
            if (bytesRead < BlockV1_0_0.START_OF_BLOCK_SEQUENCE.length) {
                break;
            }

            if (!Arrays.equals(buffer, BlockV1_0_0.START_OF_BLOCK_SEQUENCE)) {
                break;
            }

            final Block block = BlockV1_0_0.fromFile(
                file,
                file.getFilePointer() - BlockV1_0_0.START_OF_BLOCK_SEQUENCE.length
            );
            blocks.add(block);
            file.seek(block.getEndPosition());
        }

        return blocks;
    }

    private final Version asdfStandardVersion;
    private final byte[] treeBytes;
    private final List<Block> blocks;

    public LowLevelFormatV1_0_0(final Version asdfStandardVersion, final byte[] treeBytes, final List<Block> blocks) {
        this.asdfStandardVersion = asdfStandardVersion;
        this.treeBytes = treeBytes;
        this.blocks = blocks;
    }

    @Override
    public Version getAsdfStandardVersion() {
        return asdfStandardVersion;
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
