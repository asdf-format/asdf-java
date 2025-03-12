package org.asdfformat.asdf;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.asdfformat.asdf.impl.AsdfFileImpl;
import org.asdfformat.asdf.io.LowLevelFormat;
import org.asdfformat.asdf.io.LowLevelFormats;
import org.asdfformat.asdf.io.util.IOUtils;
import org.asdfformat.asdf.metadata.AsdfMetadata;
import org.asdfformat.asdf.metadata.Extension;
import org.asdfformat.asdf.metadata.HistoryEntry;
import org.asdfformat.asdf.metadata.Software;
import org.asdfformat.asdf.metadata.impl.AsdfMetadataImpl;
import org.asdfformat.asdf.metadata.impl.Extension100;
import org.asdfformat.asdf.metadata.impl.HistoryEntry100;
import org.asdfformat.asdf.metadata.impl.Software100;
import org.asdfformat.asdf.node.AsdfNode;
import org.asdfformat.asdf.node.impl.MappingAsdfNode;
import org.asdfformat.asdf.node.impl.StringAsdfNode;
import org.asdfformat.asdf.node.impl.constructor.AsdfNodeConstructor;
import org.asdfformat.asdf.util.Version;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.nodes.MappingNode;

/**
 * Main entry into this ASDF library.
 */
public class Asdf {
    private final String ASDF_TAG = "tag:stsci.edu:asdf/core/asdf-1.1.0";

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
            options.setAllowRecursiveKeys(true);
            final Yaml yaml = new Yaml(new AsdfNodeConstructor(options));

            final AsdfNode tree = yaml.load(new InputStreamReader(new ByteArrayInputStream(lowLevelFormat.getTreeBytes())));
            if (!ASDF_TAG.equals(tree.getTag())) {
                throw new IllegalStateException(String.format("Unhandled top-level tag: %s", tree.getTag()));
            }
            if (!tree.isMapping()) {
                throw new IllegalStateException("Corrupted ASDF file: Top-level node must be a mapping");
            }

            final MappingAsdfNode mappingTree = (MappingAsdfNode) tree;
            final Map<AsdfNode, AsdfNode> value = mappingTree.getValue();

            final AsdfNode asdfLibraryNode = value.remove(StringAsdfNode.of("asdf_library"));
            final AsdfNode historyNode = value.remove(StringAsdfNode.of("history"));
            final AsdfNode extensionsNode = Optional.ofNullable(historyNode)
                .flatMap(n ->n.containsKey("extension") ? Optional.of(n.get("extensions")) : Optional.empty())
                .orElse(null);
            final AsdfNode entriesNode = Optional.ofNullable(historyNode)
                .flatMap(n -> n.containsKey("entries") ? Optional.of(n.get("entries")) : Optional.empty())
                .orElse(null);

            final Software asdfLibrary = Optional.ofNullable(asdfLibraryNode)
                .map(Software100::new)
                .orElse(null);

            final String asdfVersion = "1.0.0";
            final String asdfStandardVersion = lowLevelFormat.getAsdfStandardVersion().toString();

            final List<Extension> extensions = new ArrayList<>();
            if (extensionsNode != null) {
                for (final AsdfNode extensionNode : extensionsNode) {
                    extensions.add(new Extension100(extensionNode));
                }
            }

            final List<HistoryEntry> historyEntries = new ArrayList<>();
            if (entriesNode != null) {
                for (final AsdfNode entryNode : entriesNode) {
                    historyEntries.add(new HistoryEntry100(entryNode));
                }
            }

            final AsdfMetadata metadata = new AsdfMetadataImpl(
                asdfVersion,
                asdfStandardVersion,
                asdfLibrary,
                extensions,
                historyEntries
            );

            return new AsdfFileImpl(metadata, tree);
        } catch (final Exception e) {
            IOUtils.closeQuietly(file);
            throw e;
        }
    }

    public static void main(final String[] args) throws IOException {
        final Path path = Paths.get(args[0]);

        final Asdf asdf = new Asdf();
        try (final AsdfFile asdfFile = asdf.open(path)) {
            System.out.println("Here it be!");
        }
    }
}
