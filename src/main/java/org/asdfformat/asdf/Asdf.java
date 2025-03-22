package org.asdfformat.asdf;

import org.asdfformat.asdf.impl.AsdfFileImpl;
import org.asdfformat.asdf.io.LowLevelFormat;
import org.asdfformat.asdf.io.LowLevelFormats;
import org.asdfformat.asdf.io.util.IOUtils;
import org.asdfformat.asdf.metadata.AsdfMetadata;
import org.asdfformat.asdf.ndarray.Slices;
import org.asdfformat.asdf.node.AsdfNode;
import org.asdfformat.asdf.node.impl.constructor.AsdfNodeConstructor;
import org.asdfformat.asdf.standard.AsdfStandard;
import org.asdfformat.asdf.standard.AsdfStandards;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Main entry into this ASDF library.
 */
public class Asdf {
    /**
     * Read an ASDF file from the specified filesystem path.
     * @param path path to a .asdf file
     * @return open ASDF file
     */
    AsdfFile open(final Path path) throws IOException {
        final RandomAccessFile file = new RandomAccessFile(path.toFile(), "r");
        try {
            final LowLevelFormat lowLevelFormat = LowLevelFormats.fromFile(file);
            final AsdfStandard asdfStandard = AsdfStandards.of(lowLevelFormat.getAsdfStandardVersion());

            final LoaderOptions options = new LoaderOptions();
            options.setAllowRecursiveKeys(true);
            final Yaml yaml = new Yaml(new AsdfNodeConstructor(options, lowLevelFormat, asdfStandard));

            final AsdfNode rawTree = yaml.load(new InputStreamReader(new ByteArrayInputStream(lowLevelFormat.getTreeBytes())));
            final AsdfMetadata metadata = asdfStandard.getAsdfMetadata(lowLevelFormat, rawTree);
            final AsdfNode tree = asdfStandard.getTree(rawTree);

            return new AsdfFileImpl(path, file, metadata, tree);
        } catch (final Exception e) {
            IOUtils.closeQuietly(file);
            throw e;
        }
    }

    public static void main(final String[] args) throws IOException {
        final Path path = Paths.get(args[0]);

        final Asdf asdf = new Asdf();
        try (final AsdfFile asdfFile = asdf.open(path)) {
            asdfFile.getTree().get("foo").asNdArray().slice(Slices.range(0, 50)).asLongNdArray().toArray(new long[0]);
        }
    }
}
