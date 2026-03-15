package org.asdfformat.asdf.util;

import org.asdfformat.asdf.Asdf;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileUtils {
    public static Path createTempFile(final String prefix, final String suffix) throws IOException {
        final Path path = Files.createTempFile(Asdf.getConfig().getTempPath(), prefix, suffix);

        final File file = path.toFile();
        file.deleteOnExit();

        return path;
    }
}
