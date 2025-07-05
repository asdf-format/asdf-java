package org.asdfformat.asdf.io.util;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class IOUtilsTest {
    @Mock
    private OutputStream mockOutputStream;

    @Mock
    private InputStream mockInputStream;

    @Mock
    private AutoCloseable mockAutoCloseable;

    @Nested
    class ReadUntil {
        @Test
        void testEndSequence() throws IOException {
            final byte[] data = "Hello World!!!".getBytes();
            final byte[] endSequence = "!!!".getBytes();

            final DataInputStream dataInput = new DataInputStream(new ByteArrayInputStream(data));
            final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

            final long result = IOUtils.readUntil(dataInput, endSequence, outputStream, Long.MAX_VALUE);

            assertEquals(14, result);
            assertArrayEquals(data, outputStream.toByteArray());
        }

        @Test
        void testLimitReached() throws IOException {
            final byte[] data = "Hello World!!!".getBytes();
            final byte[] endSequence = "!!!".getBytes();

            final DataInputStream dataInput = new DataInputStream(new ByteArrayInputStream(data));
            final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

            final long result = IOUtils.readUntil(dataInput, endSequence, outputStream, 5);

            assertEquals(-1, result);
            assertEquals(5, outputStream.size());
        }

        @Test
        void testEndOfInputReached() throws IOException {
            final byte[] data = "Hello World".getBytes();
            final byte[] endSequence = "!!!".getBytes();

            final DataInputStream dataInput = new DataInputStream(new ByteArrayInputStream(data));
            final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

            final long result = IOUtils.readUntil(dataInput, endSequence, outputStream, Long.MAX_VALUE);

            assertEquals(-1, result);
            assertArrayEquals(data, outputStream.toByteArray());
        }

        @Test
        void testPartialMatch() throws IOException {
            final byte[] data = "Hello!!World!!!".getBytes();
            final byte[] endSequence = "!!!".getBytes();

            final DataInputStream dataInput = new DataInputStream(new ByteArrayInputStream(data));
            final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

            final long result = IOUtils.readUntil(dataInput, endSequence, outputStream, Long.MAX_VALUE);

            assertEquals(15, result);
            assertArrayEquals(data, outputStream.toByteArray());
        }

        @Test
        void testSingleByteEndSequence() throws IOException {
            final byte[] data = "Hello World!".getBytes();
            final byte[] endSequence = "!".getBytes();

            final DataInputStream dataInput = new DataInputStream(new ByteArrayInputStream(data));
            final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

            final long result = IOUtils.readUntil(dataInput, endSequence, outputStream, Long.MAX_VALUE);

            assertEquals(12, result);
            assertArrayEquals(data, outputStream.toByteArray());
        }

        @Test
        void testEmptyInput() throws IOException {
            final byte[] data = new byte[0];
            final byte[] endSequence = "!!!".getBytes();

            final DataInputStream dataInput = new DataInputStream(new ByteArrayInputStream(data));
            final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

            final long result = IOUtils.readUntil(dataInput, endSequence, outputStream, Long.MAX_VALUE);

            assertEquals(-1, result);
            assertEquals(0, outputStream.size());
        }

        @Test
        void testEndSequenceAtStart() throws IOException {
            final byte[] data = "!!!Hello".getBytes();
            final byte[] endSequence = "!!!".getBytes();

            final DataInputStream dataInput = new DataInputStream(new ByteArrayInputStream(data));
            final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

            final long result = IOUtils.readUntil(dataInput, endSequence, outputStream, Long.MAX_VALUE);

            assertEquals(3, result);
            assertArrayEquals("!!!".getBytes(), outputStream.toByteArray());
        }

        @Test
        void testExceptionFromInputStream() throws IOException {
            final byte[] data = "Hello World".getBytes();
            final DataInputStream dataInput = new DataInputStream(new ByteArrayInputStream(data));

            doThrow(new IOException("Write failed")).when(mockOutputStream).write(anyInt());

            final byte[] endSequence = "!!!".getBytes();

            assertThrows(IOException.class, () ->
                    IOUtils.readUntil(dataInput, endSequence, mockOutputStream, Long.MAX_VALUE));
        }
    }

    @Nested
    class SeekUntil {
        @Test
        void testWithoutLimit() throws IOException {
            final byte[] data = "Hello World!!!".getBytes();
            final byte[] endSequence = "!!!".getBytes();

            final DataInputStream dataInput = new DataInputStream(new ByteArrayInputStream(data));

            final long result = IOUtils.seekUntil(dataInput, endSequence);

            assertEquals(14, result);
        }

        @Test
        void testWithLimit() throws IOException {
            final byte[] data = "Hello World!!!".getBytes();
            final byte[] endSequence = "!!!".getBytes();

            final DataInputStream dataInput = new DataInputStream(new ByteArrayInputStream(data));

            final long result = IOUtils.seekUntil(dataInput, endSequence, 100);

            assertEquals(14, result);
        }

        @Test
        void testLimitReached() throws IOException {
            final byte[] data = "Hello World!!!".getBytes();
            final byte[] endSequence = "!!!".getBytes();

            final DataInputStream dataInput = new DataInputStream(new ByteArrayInputStream(data));

            final long result = IOUtils.seekUntil(dataInput, endSequence, 5);

            assertEquals(-1, result);
        }
    }

    @Nested
    class CloseQuietly {
        @Test
        void testSuccessfulClose() throws Exception {
            IOUtils.closeQuietly(mockAutoCloseable);

            verify(mockAutoCloseable).close();
        }

        @Test
        void testExceptionOnClose() throws Exception {
            doThrow(new IOException("Test exception")).when(mockAutoCloseable).close();

            assertDoesNotThrow(() -> IOUtils.closeQuietly(mockAutoCloseable));

            verify(mockAutoCloseable).close();
        }

        @Test
        void testNullInput() {
            assertDoesNotThrow(() -> IOUtils.closeQuietly(null));
        }
    }

    @Nested
    class TransferToWithByteBuffer {
        @Test
        void testSmallInput() throws IOException {
            final byte[] data = "Hello World".getBytes();
            final ByteArrayInputStream inputStream = new ByteArrayInputStream(data);
            final ByteBuffer outputBuffer = ByteBuffer.allocate(100);

            final long result = IOUtils.transferTo(inputStream, outputBuffer);

            assertEquals(data.length, result);
            outputBuffer.flip();
            final byte[] transferred = new byte[outputBuffer.remaining()];
            outputBuffer.get(transferred);
            assertArrayEquals(data, transferred);
        }

        @Test
        void testLargeInput() throws IOException {
            final byte[] data = new byte[20000];
            for (int i = 0; i < data.length; i++) {
                data[i] = (byte) (i % 256);
            }

            final ByteArrayInputStream inputStream = new ByteArrayInputStream(data);
            final ByteBuffer outputBuffer = ByteBuffer.allocate(25000);

            final long result = IOUtils.transferTo(inputStream, outputBuffer);

            assertEquals(data.length, result);
            outputBuffer.flip();
            final byte[] transferred = new byte[outputBuffer.remaining()];
            outputBuffer.get(transferred);
            assertArrayEquals(data, transferred);
        }

        @Test
        void testEmptyInput() throws IOException {
            final ByteArrayInputStream inputStream = new ByteArrayInputStream(new byte[0]);
            final ByteBuffer outputBuffer = ByteBuffer.allocate(100);

            final long result = IOUtils.transferTo(inputStream, outputBuffer);

            assertEquals(0, result);
            assertEquals(0, outputBuffer.position());
        }

        @Test
        void testInsufficientSpace() throws IOException {
            final byte[] data = "Hello World".getBytes();
            final ByteArrayInputStream inputStream = new ByteArrayInputStream(data);
            final ByteBuffer outputBuffer = ByteBuffer.allocate(5);

            assertThrows(Exception.class, () -> IOUtils.transferTo(inputStream, outputBuffer));
        }

        @Test
        void testExceptionPropagated() throws IOException {
            when(mockInputStream.read(any(byte[].class), anyInt(), anyInt()))
                    .thenThrow(new IOException("Test exception"));

            final ByteBuffer outputBuffer = ByteBuffer.allocate(100);

            assertThrows(IOException.class, () -> IOUtils.transferTo(mockInputStream, outputBuffer));
        }
    }

    @Nested
    class TransferToWithOutputStream {
        @Test
        void testSmallInput() throws IOException {
            final byte[] data = "Hello World".getBytes();
            final ByteArrayInputStream inputStream = new ByteArrayInputStream(data);
            final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

            final long result = IOUtils.transferTo(inputStream, outputStream);

            assertEquals(data.length, result);
            assertArrayEquals(data, outputStream.toByteArray());
        }

        @Test
        void testLargeInput() throws IOException {
            final byte[] data = new byte[20000];
            for (int i = 0; i < data.length; i++) {
                data[i] = (byte) (i % 256);
            }

            final ByteArrayInputStream inputStream = new ByteArrayInputStream(data);
            final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

            final long result = IOUtils.transferTo(inputStream, outputStream);

            assertEquals(data.length, result);
            assertArrayEquals(data, outputStream.toByteArray());
        }

        @Test
        void testEmptyInput() throws IOException {
            final ByteArrayInputStream inputStream = new ByteArrayInputStream(new byte[0]);
            final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

            final long result = IOUtils.transferTo(inputStream, outputStream);

            assertEquals(0, result);
            assertEquals(0, outputStream.size());
        }
    }
}