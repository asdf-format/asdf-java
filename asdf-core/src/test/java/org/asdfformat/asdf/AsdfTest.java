package org.asdfformat.asdf;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AsdfTest {
    @Nested
    public class Config {
        @AfterEach
        void afterEach() {
            Asdf.configure(b -> b.tempPath(AsdfConfig.DEFAULT.getTempPath()));
        }

        @Test
        void testDefaultConfig() {
            assertEquals(Paths.get(System.getProperty("java.io.tmpdir")), Asdf.getConfig().getTempPath());
        }

        @Test
        void testCustomConfig() {
            Asdf.configure(b -> b.tempPath(Paths.get("/some/custom/path")));
            assertEquals(Paths.get("/some/custom/path"), Asdf.getConfig().getTempPath());
        }
    }
}
