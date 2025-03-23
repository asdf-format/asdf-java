package org.asdfformat.asdf;

import java.io.IOException;

import org.asdfformat.asdf.metadata.AsdfMetadata;
import org.asdfformat.asdf.node.AsdfNode;


/**
 * An open ASDF file. Must be closed to free resources.
 */
public interface AsdfFile extends AutoCloseable {

    /**
     * Returns the ASDF file metadata, which is the
     * non-science metadata describing the file itself.
     * @return ASDF file metadata
     */
    AsdfMetadata getMetadata();

    /**
     * Returns the root node of the ASDF "tree", which contains
     * the file's science metadata and data.
     * @return root node of the ASDF tree
     */
    AsdfNode getTree();

    @Override
    void close() throws IOException;
}
