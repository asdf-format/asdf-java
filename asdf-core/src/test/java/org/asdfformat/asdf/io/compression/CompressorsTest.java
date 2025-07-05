package org.asdfformat.asdf.io.compression;

import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CompressorsTest {
    @Test
    void testZlibCompressor() {
        final Compressor compressor = Compressors.of("zlib".getBytes(StandardCharsets.UTF_8));
        assertInstanceOf(ZlibCompressor.class, compressor);
    }

    @Test
    void testUnrecognizedIdentifier() {
        final RuntimeException exception = assertThrows(RuntimeException.class, () -> Compressors.of("foo".getBytes(StandardCharsets.UTF_8)));
        assertTrue(exception.getMessage().contains("Unrecognized compression identifier"));
    }
}
