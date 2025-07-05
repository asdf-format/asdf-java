package org.asdfformat.asdf.io.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class NullOutputStreamTest {
    private NullOutputStream nullOutputStream;

    @BeforeEach
    void beforeEach() {
        nullOutputStream = new NullOutputStream();
    }


    @Test
    void testSingleByte() throws IOException {
        assertDoesNotThrow(() -> nullOutputStream.write(65));
    }

    @Test
    void testZero() throws IOException {
        assertDoesNotThrow(() -> nullOutputStream.write(0));
    }

    @Test
    void testNegativeValue() throws IOException {
        assertDoesNotThrow(() -> nullOutputStream.write(-1));
    }

    @Test
    void testMaxValue() throws IOException {
        assertDoesNotThrow(() -> nullOutputStream.write(255));
    }

    @Test
    void testMultipleCalls() throws IOException {
        assertDoesNotThrow(() -> {
            nullOutputStream.write(65);
            nullOutputStream.write(66);
            nullOutputStream.write(67);
        });
    }
}
