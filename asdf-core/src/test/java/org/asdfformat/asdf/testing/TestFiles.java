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
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.Map;

public class TestFiles {
    private static final String PYTHON_PATH = "/opt/homebrew/Caskroom/miniconda/base/envs/asdf-fast/bin/python";
    private static final String GENERATOR_SCRIPT_PATH = "/Users/eslavich/repos/asdf-java/test-file-generator/test_file_generator.py";

    private static final Map<String, Path> TEST_FILES = new HashMap<>();

    public static Path getPath(final TestFileType testFileType, final Version asdfStandardVersion) {
        final String key = makeKey(testFileType, asdfStandardVersion);

        if (!TEST_FILES.containsKey(key)) {
            TEST_FILES.put(key, generateTestFile(testFileType, asdfStandardVersion));
        }

        return TEST_FILES.get(key);
    }

    @SneakyThrows(IOException.class)
    private static Path generateTestFile(final TestFileType testFileType, final Version asdfStandardVersion) {
        try (final InputStream scriptInputStream = TestFiles.class.getResourceAsStream(testFileType.getScriptResourceName())) {
            if (scriptInputStream == null) {
                throw new RuntimeException("Missing generator script for " + testFileType);
            }

            final File file = File.createTempFile(testFileType + "-", ".asdf");
            file.deleteOnExit();
            final Path path = file.toPath();

            final Process process = new ProcessBuilder(PYTHON_PATH, GENERATOR_SCRIPT_PATH, "--version", asdfStandardVersion.toString()).start();
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

    private static String makeKey(final TestFileType testFileType, final Version asdfStandardVersion) {
        return String.format("%s:%s", testFileType, asdfStandardVersion);
    }
}
