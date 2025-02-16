package org.asdfformat.asdf.metadata;

import java.time.OffsetDateTime;
import java.util.List;

/**
 * Metadata describing a modification of an ASDF file.
 */
public interface HistoryEntry {

    /**
     * Get the description of the modification.
     */
    String getDescription();

    /**
     * Get the time of modification.
     */
    OffsetDateTime getTime();

    /**
     * Get a list of software packages used to make the modification.
     */
    List<Software> getSoftwares();
}
