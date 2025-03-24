package org.asdfformat.asdf.metadata;

/**
 * Metadata describing a software package.
 */
public interface Software {

    /**
     * The name of the application or library.
     */
    String getName();

    /**
     * The author (or institution) that produced the software package.
     */
    String getAuthor();

    /**
     * A URI to the homepage of the software.
     */
    String getHomepage();

    /**
     * The version of the software used.
     */
    String getVersion();
}
