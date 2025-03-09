package org.asdfformat.asdf.metadata;

import java.time.OffsetDateTime;
import java.util.List;

/**
 * Metadata describing a modification of an ASDF file.
 */
public interface HistoryEntry {

    /**
     * A description of the transformation performed.
     */
    String getDescription();

    /**
     * A timestamp for the operation, in UTC.
     */
    OffsetDateTime getTime();

    /**
     * Descriptions of the software that performed the operation.
     */
    List<Software> getSoftwares();
}
