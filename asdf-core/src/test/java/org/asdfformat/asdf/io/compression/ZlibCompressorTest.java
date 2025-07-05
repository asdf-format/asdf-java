package org.asdfformat.asdf.io.compression;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.zip.DeflaterOutputStream;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ZlibCompressorTest {
    private ZlibCompressor compressor;

    @BeforeEach
    void beforeEach() {
        compressor = new ZlibCompressor();
    }

    @Nested
    class GetIdentifier {
        @Test
        void testGetIdentifier() {
            final byte[] identifier = compressor.getIdentifier();

            assertNotNull(identifier);
            assertArrayEquals("zlib".getBytes(StandardCharsets.UTF_8), identifier);
        }
    }

    @Nested
    class Decompress {
        @Test
        void testDecompressSimpleText() throws IOException {
            final String originalText = "Hello World";
            final byte[] compressedData = compressWithZlib(originalText.getBytes());

            final ByteBuffer inputBuffer = ByteBuffer.wrap(compressedData);
            final ByteBuffer outputBuffer = ByteBuffer.allocate(1024);

            final long decompressedSize = compressor.decompress(inputBuffer, outputBuffer);

            assertEquals(originalText.length(), decompressedSize);
            outputBuffer.flip();
            final byte[] decompressedData = new byte[outputBuffer.remaining()];
            outputBuffer.get(decompressedData);
            assertArrayEquals(originalText.getBytes(), decompressedData);
        }

        @Test
        void testDecompressEmptyData() throws IOException {
            final byte[] compressedData = compressWithZlib(new byte[0]);

            final ByteBuffer inputBuffer = ByteBuffer.wrap(compressedData);
            final ByteBuffer outputBuffer = ByteBuffer.allocate(1024);

            final long decompressedSize = compressor.decompress(inputBuffer, outputBuffer);

            assertEquals(0, decompressedSize);
            assertEquals(0, outputBuffer.position());
        }

        @Test
        void testDecompressLargeData() throws IOException {
            final byte[] originalData = new byte[10000];
            for (int i = 0; i < originalData.length; i++) {
                originalData[i] = (byte) (i % 256);
            }
            final byte[] compressedData = compressWithZlib(originalData);

            final ByteBuffer inputBuffer = ByteBuffer.wrap(compressedData);
            final ByteBuffer outputBuffer = ByteBuffer.allocate(15000);

            final long decompressedSize = compressor.decompress(inputBuffer, outputBuffer);

            assertEquals(originalData.length, decompressedSize);
            outputBuffer.flip();
            final byte[] decompressedData = new byte[outputBuffer.remaining()];
            outputBuffer.get(decompressedData);
            assertArrayEquals(originalData, decompressedData);
        }

        @Test
        void testMultipleDecompressions() throws IOException {
            final String text1 = "First text";
            final String text2 = "Second text";
            final byte[] compressed1 = compressWithZlib(text1.getBytes());
            final byte[] compressed2 = compressWithZlib(text2.getBytes());

            final ByteBuffer inputBuffer1 = ByteBuffer.wrap(compressed1);
            final ByteBuffer outputBuffer1 = ByteBuffer.allocate(1024);
            final long size1 = compressor.decompress(inputBuffer1, outputBuffer1);

            final ByteBuffer inputBuffer2 = ByteBuffer.wrap(compressed2);
            final ByteBuffer outputBuffer2 = ByteBuffer.allocate(1024);
            final long size2 = compressor.decompress(inputBuffer2, outputBuffer2);

            assertEquals(text1.length(), size1);
            assertEquals(text2.length(), size2);

            outputBuffer1.flip();
            final byte[] result1 = new byte[outputBuffer1.remaining()];
            outputBuffer1.get(result1);
            assertArrayEquals(text1.getBytes(), result1);

            outputBuffer2.flip();
            final byte[] result2 = new byte[outputBuffer2.remaining()];
            outputBuffer2.get(result2);
            assertArrayEquals(text2.getBytes(), result2);
        }

        @Test
        void testDecompressWithBufferReuse() throws IOException {
            final String text1 = "First text";
            final String text2 = "Second longer text";
            final byte[] compressed1 = compressWithZlib(text1.getBytes());
            final byte[] compressed2 = compressWithZlib(text2.getBytes());

            final ByteBuffer outputBuffer = ByteBuffer.allocate(1024);

            final ByteBuffer inputBuffer1 = ByteBuffer.wrap(compressed1);
            final long size1 = compressor.decompress(inputBuffer1, outputBuffer);

            outputBuffer.flip();
            final byte[] result1 = new byte[outputBuffer.remaining()];
            outputBuffer.get(result1);
            assertArrayEquals(text1.getBytes(), result1);

            outputBuffer.clear();

            final ByteBuffer inputBuffer2 = ByteBuffer.wrap(compressed2);
            final long size2 = compressor.decompress(inputBuffer2, outputBuffer);

            outputBuffer.flip();
            final byte[] result2 = new byte[outputBuffer.remaining()];
            outputBuffer.get(result2);
            assertArrayEquals(text2.getBytes(), result2);

            assertEquals(text1.length(), size1);
            assertEquals(text2.length(), size2);
        }
    }

    private byte[] compressWithZlib(final byte[] data) {
        try {
            final ByteArrayOutputStream baos = new ByteArrayOutputStream();
            try (final DeflaterOutputStream dos = new DeflaterOutputStream(baos)) {
                dos.write(data);
            }
            return baos.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException("Failed to compress test data", e);
        }
    }
}