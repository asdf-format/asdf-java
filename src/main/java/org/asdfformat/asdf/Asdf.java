package org.asdfformat.asdf;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.io.StringReader;
import java.nio.LongBuffer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.asdfformat.asdf.io.Block;
import org.asdfformat.asdf.io.LowLevelFormat;
import org.asdfformat.asdf.io.LowLevelFormats;
import org.asdfformat.asdf.io.util.IOUtils;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.nodes.Node;

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

            final Yaml yaml = new Yaml();
            final Node node = yaml.compose(new InputStreamReader(new ByteArrayInputStream(lowLevelFormat.getTreeBytes())));

            System.out.println(node.getTag().getValue());

            final Block block = lowLevelFormat.getBlock(0);
            final LongBuffer longBuffer = block.getDataBuffer().asLongBuffer();
            for (int i = 0; i < longBuffer.limit(); i++) {
                System.out.println(Long.reverseBytes(longBuffer.get(i)));
            }


            return null;
        } catch (final Exception e) {
            IOUtils.closeQuietly(file);
            throw e;
        }
    }

    public static void main(final String[] args) throws IOException {
        final Path path = Paths.get(args[0]);

        final Asdf asdf = new Asdf();
        try (final AsdfFile asdfFile = asdf.open(path)) {

        }
    }
}
