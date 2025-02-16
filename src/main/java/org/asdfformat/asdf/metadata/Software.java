package org.asdfformat.asdf.metadata;

/**
 * Metadata describing a software package.
 */
public interface Software {

    /**
     * Get the name of the software package.
     */
    String getName();

    /**
     * Get the version of the software package.
     */
    String getVersion();
}
