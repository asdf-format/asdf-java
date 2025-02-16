package org.asdfformat.asdf.metadata;

import java.util.List;

/**
 * ASDF file metadata.
 */
public interface AsdfMetadata {

    /**
     * Get the low-level ASDF version of this file.
     * @return ASDF version
     */
    String getAsdfVersion();

    /**
     * Get the ASDF Standard version of this file.
     * @return ASDF Standard version
     */
    String getAsdfStandardVersion();

    /**
     * Get metadata on the ASDF library
     * that produced this file.
     * @return ASDF library software metadata
     */
    Software getAsdfLibrary();

    /**
     * Get the list of extensions to the ASDF Standard
     * that are used in this file.
     * @return list of extensions
     */
    List<Extension> getExtensions();

    /**
     * Get the history of modifications to this file.
     * @return list of history entries
     */
    List<HistoryEntry> getHistory();
}
