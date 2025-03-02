package org.asdfformat.asdf;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.nio.LongBuffer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Optional;

import org.asdfformat.asdf.io.LowLevelFormat;
import org.asdfformat.asdf.io.LowLevelFormats;
import org.asdfformat.asdf.io.util.IOUtils;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.comments.CommentLine;
import org.yaml.snakeyaml.nodes.MappingNode;
import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.nodes.NodeTuple;
import org.yaml.snakeyaml.nodes.ScalarNode;

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

            final LoaderOptions options = new LoaderOptions();
            options.setProcessComments(true);
            final Yaml yaml = new Yaml(options);

            System.out.println(new String(lowLevelFormat.getTreeBytes(), StandardCharsets.UTF_8));

            final Node node = yaml.compose(new InputStreamReader(new ByteArrayInputStream(lowLevelFormat.getTreeBytes())));

            if (node instanceof MappingNode) {
                final MappingNode mappingNode = (MappingNode)node;

                System.out.println("Block comments:");
                for (final CommentLine comment : Optional.ofNullable(node.getBlockComments()).orElseGet(Collections::emptyList)) {
                    System.out.println(comment.getValue());
                }

                System.out.println("Inline comments:");
                for (final CommentLine comment : Optional.ofNullable(node.getInLineComments()).orElseGet(Collections::emptyList)) {
                    System.out.println(comment.getValue());
                }

                System.out.println("End comments:");
                for (final CommentLine comment : Optional.ofNullable(node.getEndComments()).orElseGet(Collections::emptyList)) {
                    System.out.println(comment.getValue());
                }

                for (final NodeTuple tuple : mappingNode.getValue()) {
                    final Node keyNode = tuple.getKeyNode();
                    if (keyNode instanceof ScalarNode) {
                        final ScalarNode scalarNode = (ScalarNode)keyNode;
                        System.out.println(scalarNode.getValue());
                    }
                    final Node valueNode = tuple.getValueNode();
                    if (valueNode instanceof ScalarNode) {
                        final ScalarNode scalarValueNode = (ScalarNode)valueNode;
                        System.out.println(scalarValueNode);
                        System.out.println(scalarValueNode.getValue());
                    }
                    System.out.println(tuple.getKeyNode());
                }
            }
            System.out.println(node.getTag().getValue());
            System.out.println(node.getClass().getCanonicalName());

            //final Block block = lowLevelFormat.getBlock(0);
//            final LongBuffer longBuffer = block.getDataBuffer().asLongBuffer();
//            for (int i = 0; i < longBuffer.limit(); i++) {
//                System.out.println(Long.reverseBytes(longBuffer.get(i)));
//            }


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
