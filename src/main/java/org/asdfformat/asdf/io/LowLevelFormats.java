package org.asdfformat.asdf.io;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import org.asdfformat.asdf.io.impl.LowLevelFormatV1Impl;
import org.asdfformat.asdf.io.util.IOUtils;

public class LowLevelFormats {
    private static final byte[] ASDF_MAGIC = "#ASDF".getBytes(StandardCharsets.UTF_8);
    private static final byte[] END_OF_VERSION = "\n".getBytes(StandardCharsets.UTF_8);

    public static final long MAX_TREE_LENGTH = 2L * 1024L * 1024L * 1024L;

    public static LowLevelFormat fromFile(final RandomAccessFile file) throws IOException {
        file.seek(0);

        long bytesRead;

        final byte[] magic = new byte[ASDF_MAGIC.length];
        bytesRead = file.read(magic);
        if (!(bytesRead == ASDF_MAGIC.length && Arrays.equals(magic, ASDF_MAGIC))) {
            throw new IOException("File is not a valid ASDF file");
        }

        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bytesRead = IOUtils.readUntil(file, END_OF_VERSION, outputStream, 100);
        if (bytesRead < 0) {
            throw new IOException("File does not a valid ASDF file");
        }

        final byte[] version = outputStream.toByteArray();
        if (Arrays.equals(version, LowLevelFormatV1Impl.VERSION_IDENTIFIER)) {
            return LowLevelFormatV1Impl.fromFile(file);
        } else {
            throw new IOException("Unsupported low-level ASDF version");
        }
    }
}
