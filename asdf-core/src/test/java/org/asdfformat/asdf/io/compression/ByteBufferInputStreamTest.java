package org.asdfformat.asdf.io.compression;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.nio.ByteBuffer;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ByteBufferInputStreamTest {

    @Nested
    class Constructor {
        @Test
        void testConstructorWithData() {
            final byte[] data = "Hello World".getBytes();
            final ByteBuffer buffer = ByteBuffer.wrap(data);
            final ByteBufferInputStream stream = new ByteBufferInputStream(buffer);

            assertEquals(data.length, stream.available());
        }

        @Test
        void testConstructorWithEmptyBuffer() {
            final ByteBuffer buffer = ByteBuffer.allocate(0);
            final ByteBufferInputStream stream = new ByteBufferInputStream(buffer);

            assertEquals(0, stream.available());
        }

        @Test
        void testConstructorPreservesOriginalBuffer() {
            final byte[] data = "Hello World".getBytes();
            final ByteBuffer buffer = ByteBuffer.wrap(data);
            final int originalPosition = 5;
            buffer.position(originalPosition);

            final ByteBufferInputStream stream = new ByteBufferInputStream(buffer);

            assertEquals(originalPosition, buffer.position());
            assertEquals(data.length - originalPosition, stream.available());
        }

        @Test
        void testConstructorWithPartialBuffer() {
            final byte[] data = "Hello World".getBytes();
            final ByteBuffer buffer = ByteBuffer.wrap(data);
            buffer.position(6);
            buffer.limit(10);

            final ByteBufferInputStream stream = new ByteBufferInputStream(buffer);

            assertEquals(4, stream.available());
        }
    }

    @Nested
    class ReadSingleByte {
        @Test
        void testReadSingleByte() {
            final byte[] data = "Hello".getBytes();
            final ByteBuffer buffer = ByteBuffer.wrap(data);
            final ByteBufferInputStream stream = new ByteBufferInputStream(buffer);

            assertEquals('H', stream.read());
            assertEquals('e', stream.read());
            assertEquals('l', stream.read());
            assertEquals('l', stream.read());
            assertEquals('o', stream.read());
        }

        @Test
        void testReadSingleByteEndOfStream() {
            final byte[] data = "A".getBytes();
            final ByteBuffer buffer = ByteBuffer.wrap(data);
            final ByteBufferInputStream stream = new ByteBufferInputStream(buffer);

            assertEquals('A', stream.read());
            assertEquals(-1, stream.read());
            assertEquals(-1, stream.read());
        }

        @Test
        void testReadSingleByteEmptyStream() {
            final ByteBuffer buffer = ByteBuffer.allocate(0);
            final ByteBufferInputStream stream = new ByteBufferInputStream(buffer);

            assertEquals(-1, stream.read());
        }

        @Test
        void testReadSingleByteUnsignedConversion() {
            final byte[] data = {(byte) 0xFF, (byte) 0x80, (byte) 0x7F};
            final ByteBuffer buffer = ByteBuffer.wrap(data);
            final ByteBufferInputStream stream = new ByteBufferInputStream(buffer);

            assertEquals(255, stream.read());
            assertEquals(128, stream.read());
            assertEquals(127, stream.read());
        }
    }

    @Nested
    class ReadByteArray {
        @Test
        void testReadByteArray() {
            final byte[] data = "Hello World".getBytes();
            final ByteBuffer buffer = ByteBuffer.wrap(data);
            final ByteBufferInputStream stream = new ByteBufferInputStream(buffer);

            final byte[] result = new byte[5];
            final int bytesRead = stream.read(result, 0, 5);

            assertEquals(5, bytesRead);
            assertArrayEquals("Hello".getBytes(), result);
        }

        @Test
        void testReadByteArrayWithOffset() {
            final byte[] data = "Hello World".getBytes();
            final ByteBuffer buffer = ByteBuffer.wrap(data);
            final ByteBufferInputStream stream = new ByteBufferInputStream(buffer);

            final byte[] result = new byte[10];
            final int bytesRead = stream.read(result, 2, 5);

            assertEquals(5, bytesRead);
            final byte[] expected = new byte[10];
            System.arraycopy("Hello".getBytes(), 0, expected, 2, 5);
            assertArrayEquals(expected, result);
        }

        @Test
        void testReadByteArrayZeroLength() {
            final byte[] data = "Hello World".getBytes();
            final ByteBuffer buffer = ByteBuffer.wrap(data);
            final ByteBufferInputStream stream = new ByteBufferInputStream(buffer);

            final byte[] result = new byte[5];
            final int bytesRead = stream.read(result, 0, 0);

            assertEquals(0, bytesRead);
        }

        @Test
        void testReadByteArrayEndOfStream() {
            final byte[] data = "Hi".getBytes();
            final ByteBuffer buffer = ByteBuffer.wrap(data);
            final ByteBufferInputStream stream = new ByteBufferInputStream(buffer);

            final byte[] result = new byte[5];
            final int bytesRead = stream.read(result, 0, 5);

            assertEquals(2, bytesRead);
            assertEquals('H', result[0]);
            assertEquals('i', result[1]);
        }

        @Test
        void testReadByteArrayEmptyStream() {
            final ByteBuffer buffer = ByteBuffer.allocate(0);
            final ByteBufferInputStream stream = new ByteBufferInputStream(buffer);

            final byte[] result = new byte[5];
            final int bytesRead = stream.read(result, 0, 5);

            assertEquals(-1, bytesRead);
        }

        @Test
        void testReadByteArrayMultipleCalls() {
            final byte[] data = "Hello World".getBytes();
            final ByteBuffer buffer = ByteBuffer.wrap(data);
            final ByteBufferInputStream stream = new ByteBufferInputStream(buffer);

            final byte[] result1 = new byte[5];
            final byte[] result2 = new byte[6];

            assertEquals(5, stream.read(result1, 0, 5));
            assertEquals(6, stream.read(result2, 0, 6));

            assertArrayEquals("Hello".getBytes(), result1);
            assertArrayEquals(" World".getBytes(), result2);
        }
    }

    @Nested
    class Skip {
        @Test
        void testSkipBytes() {
            final byte[] data = "Hello World".getBytes();
            final ByteBuffer buffer = ByteBuffer.wrap(data);
            final ByteBufferInputStream stream = new ByteBufferInputStream(buffer);

            final long skipped = stream.skip(6);

            assertEquals(6, skipped);
            assertEquals('W', stream.read());
        }

        @Test
        void testSkipZeroBytes() {
            final byte[] data = "Hello World".getBytes();
            final ByteBuffer buffer = ByteBuffer.wrap(data);
            final ByteBufferInputStream stream = new ByteBufferInputStream(buffer);

            final long skipped = stream.skip(0);

            assertEquals(0, skipped);
            assertEquals('H', stream.read());
        }

        @Test
        void testSkipMoreThanAvailable() {
            final byte[] data = "Hello".getBytes();
            final ByteBuffer buffer = ByteBuffer.wrap(data);
            final ByteBufferInputStream stream = new ByteBufferInputStream(buffer);

            final long skipped = stream.skip(100);

            assertEquals(5, skipped);
            assertEquals(-1, stream.read());
        }

        @Test
        void testSkipEmptyStream() {
            final ByteBuffer buffer = ByteBuffer.allocate(0);
            final ByteBufferInputStream stream = new ByteBufferInputStream(buffer);

            final long skipped = stream.skip(10);

            assertEquals(-1, skipped);
        }

        @Test
        void testSkipAfterPartialRead() {
            final byte[] data = "Hello World".getBytes();
            final ByteBuffer buffer = ByteBuffer.wrap(data);
            final ByteBufferInputStream stream = new ByteBufferInputStream(buffer);

            assertEquals('H', stream.read());
            assertEquals('e', stream.read());

            final long skipped = stream.skip(3);

            assertEquals(3, skipped);
            assertEquals(' ', stream.read());
        }
    }

    @Nested
    class MarkAndReset {
        @Test
        void testMarkAndReset() {
            final byte[] data = "Hello World".getBytes();
            final ByteBuffer buffer = ByteBuffer.wrap(data);
            final ByteBufferInputStream stream = new ByteBufferInputStream(buffer);

            assertEquals('H', stream.read());
            assertEquals('e', stream.read());

            stream.mark(0);
            assertEquals('l', stream.read());
            assertEquals('l', stream.read());

            stream.reset();
            assertEquals('l', stream.read());
            assertEquals('l', stream.read());
        }

        @Test
        void testMarkSupported() {
            final byte[] data = "Hello World".getBytes();
            final ByteBuffer buffer = ByteBuffer.wrap(data);
            final ByteBufferInputStream stream = new ByteBufferInputStream(buffer);

            assertTrue(stream.markSupported());
        }

        @Test
        void testInitialMark() {
            final byte[] data = "Hello World".getBytes();
            final ByteBuffer buffer = ByteBuffer.wrap(data);
            final ByteBufferInputStream stream = new ByteBufferInputStream(buffer);

            assertEquals('H', stream.read());
            assertEquals('e', stream.read());

            stream.reset();
            assertEquals('H', stream.read());
        }

        @Test
        void testMultipleMarks() {
            final byte[] data = "Hello World".getBytes();
            final ByteBuffer buffer = ByteBuffer.wrap(data);
            final ByteBufferInputStream stream = new ByteBufferInputStream(buffer);

            stream.mark(0);
            assertEquals('H', stream.read());

            stream.mark(0);
            assertEquals('e', stream.read());
            assertEquals('l', stream.read());

            stream.reset();
            assertEquals('e', stream.read());
        }

        @Test
        void testResetAfterSkip() {
            final byte[] data = "Hello World".getBytes();
            final ByteBuffer buffer = ByteBuffer.wrap(data);
            final ByteBufferInputStream stream = new ByteBufferInputStream(buffer);

            stream.mark(0);
            stream.skip(5);
            assertEquals(' ', stream.read());

            stream.reset();
            assertEquals('H', stream.read());
        }

        @Test
        void testMarkWithPartialBuffer() {
            final byte[] data = "Hello World".getBytes();
            final ByteBuffer buffer = ByteBuffer.wrap(data);
            buffer.position(6);
            final ByteBufferInputStream stream = new ByteBufferInputStream(buffer);

            assertEquals('W', stream.read());
            stream.mark(0);
            assertEquals('o', stream.read());

            stream.reset();
            assertEquals('o', stream.read());
        }
    }

    @Nested
    class Available {
        @Test
        void testAvailableFullBuffer() {
            final byte[] data = "Hello World".getBytes();
            final ByteBuffer buffer = ByteBuffer.wrap(data);
            final ByteBufferInputStream stream = new ByteBufferInputStream(buffer);

            assertEquals(11, stream.available());
        }

        @Test
        void testAvailableAfterReading() {
            final byte[] data = "Hello World".getBytes();
            final ByteBuffer buffer = ByteBuffer.wrap(data);
            final ByteBufferInputStream stream = new ByteBufferInputStream(buffer);

            stream.read();
            assertEquals(10, stream.available());

            stream.read(new byte[5], 0, 5);
            assertEquals(5, stream.available());
        }

        @Test
        void testAvailableAfterSkip() {
            final byte[] data = "Hello World".getBytes();
            final ByteBuffer buffer = ByteBuffer.wrap(data);
            final ByteBufferInputStream stream = new ByteBufferInputStream(buffer);

            stream.skip(6);
            assertEquals(5, stream.available());
        }

        @Test
        void testAvailableEmptyStream() {
            final ByteBuffer buffer = ByteBuffer.allocate(0);
            final ByteBufferInputStream stream = new ByteBufferInputStream(buffer);

            assertEquals(0, stream.available());
        }

        @Test
        void testAvailableAfterReset() {
            final byte[] data = "Hello World".getBytes();
            final ByteBuffer buffer = ByteBuffer.wrap(data);
            final ByteBufferInputStream stream = new ByteBufferInputStream(buffer);

            stream.read();
            stream.read();
            stream.mark(0);
            stream.read();

            assertEquals(8, stream.available());

            stream.reset();
            assertEquals(9, stream.available());
        }

        @Test
        void testAvailableWithPartialBuffer() {
            final byte[] data = "Hello World".getBytes();
            final ByteBuffer buffer = ByteBuffer.wrap(data);
            buffer.position(6);
            buffer.limit(10);
            final ByteBufferInputStream stream = new ByteBufferInputStream(buffer);

            assertEquals(4, stream.available());
        }
    }

    @Nested
    class EdgeCases {
        @Test
        void testLargeBuffer() {
            final byte[] data = new byte[10000];
            for (int i = 0; i < data.length; i++) {
                data[i] = (byte) (i % 256);
            }
            final ByteBuffer buffer = ByteBuffer.wrap(data);
            final ByteBufferInputStream stream = new ByteBufferInputStream(buffer);

            assertEquals(10000, stream.available());

            final byte[] result = new byte[1000];
            final int bytesRead = stream.read(result, 0, 1000);

            assertEquals(1000, bytesRead);
            assertEquals(9000, stream.available());

            for (int i = 0; i < 1000; i++) {
                assertEquals((byte) (i % 256), result[i]);
            }
        }

        @Test
        void testDirectByteBuffer() {
            final ByteBuffer buffer = ByteBuffer.allocateDirect(10);
            buffer.put("Hello".getBytes());
            buffer.flip();
            final ByteBufferInputStream stream = new ByteBufferInputStream(buffer);

            assertEquals(5, stream.available());
            assertEquals('H', stream.read());
            assertEquals('e', stream.read());
        }

        @Test
        void testReadOnlyBuffer() {
            final byte[] data = "Hello World".getBytes();
            final ByteBuffer buffer = ByteBuffer.wrap(data).asReadOnlyBuffer();
            final ByteBufferInputStream stream = new ByteBufferInputStream(buffer);

            assertEquals(11, stream.available());
            assertEquals('H', stream.read());

            final byte[] result = new byte[5];
            final int bytesRead = stream.read(result, 0, 5);
            assertEquals(5, bytesRead);
            assertArrayEquals("ello ".getBytes(), result);
        }
    }
}