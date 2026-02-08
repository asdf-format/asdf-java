package org.asdfformat.asdf.testing;

import lombok.SneakyThrows;
import org.asdfformat.asdf.io.util.IOUtils;
import org.asdfformat.asdf.util.Version;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.junit.Assume.assumeTrue;

public class ReferenceFileUtils {
    private static final String PYTHON_PATH = System.getenv("ASDF_JAVA_TESTS_PYTHON_PATH");
    private static final Path TEST_FILE_GENERATOR_PY_PATH = getTestFileGeneratorPyPath();

    @SneakyThrows(IOException.class)
    private static Path getTestFileGeneratorPyPath() {
        final File file = File.createTempFile("test_file_generator_", ".py");
        file.deleteOnExit();
        final Path path = file.toPath();

        try (
                final InputStream inputStream = Optional.ofNullable(ReferenceFileUtils.class.getResourceAsStream("/testing/reference_file_generator.py"))
                        .orElseThrow(() -> new RuntimeException("Missing testing/reference_file_generator.py"));
                final OutputStream outputStream = Files.newOutputStream(path, StandardOpenOption.CREATE)
        ) {
            IOUtils.transferTo(inputStream, outputStream);
        }

        return path;
    }

    private static final Map<String, Path> TEST_FILES = new HashMap<>();

    public static Path getPath(final ReferenceFile testfile, final Version asdfStandardVersion) {
        final String key = makeKey(testfile, asdfStandardVersion);

        if (!TEST_FILES.containsKey(key)) {
            TEST_FILES.put(key, generateTestFile(testfile, asdfStandardVersion));
        }

        return TEST_FILES.get(key);
    }

    @SneakyThrows(IOException.class)
    private static Path generateTestFile(final ReferenceFile referenceFile, final Version asdfStandardVersion) {
        try (final InputStream scriptInputStream = referenceFile.openScript()) {
            if (scriptInputStream == null) {
                throw new RuntimeException("Missing generator script for " + referenceFile.getName());
            }

            final File file = File.createTempFile(referenceFile.getName() + "-", ".asdf");
            file.deleteOnExit();
            final Path path = file.toPath();

            assumeTrue(
                    "ASDF_JAVA_TESTS_PYTHON_PATH missing or unset",
                    Optional.ofNullable(PYTHON_PATH)
                            .map(p -> p.isEmpty() ? null : p)
                            .map(p -> Files.exists(Paths.get(p)))
                            .orElse(false)
            );
            final Process process = new ProcessBuilder(PYTHON_PATH, TEST_FILE_GENERATOR_PY_PATH.toString(), "--version", asdfStandardVersion.toString()).start();
            try (final OutputStream outputStream = process.getOutputStream()) {
                IOUtils.transferTo(scriptInputStream, outputStream);
            }

            try (
                    final InputStream asdfInputStream = process.getInputStream();
                    final OutputStream asdfOutputStream = Files.newOutputStream(path, StandardOpenOption.CREATE)
            ) {
                IOUtils.transferTo(asdfInputStream, asdfOutputStream);
            }

            return path;
        }
    }

    private static String makeKey(final ReferenceFile referenceFile, final Version asdfStandardVersion) {
        return String.format("%s:%s", referenceFile, asdfStandardVersion);
    }
}
