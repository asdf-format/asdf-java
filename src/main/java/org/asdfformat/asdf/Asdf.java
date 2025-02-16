package org.asdfformat.asdf;

import java.nio.file.Path;

/**
 * Main entry into this ASDF library.
 */
public interface Asdf {

    /**
     * Read an ASDF file from the specified filesystem path.
     * @param path path to a .asdf file
     * @return open ASDF file
     */
    AsdfFile open(Path path);
}
