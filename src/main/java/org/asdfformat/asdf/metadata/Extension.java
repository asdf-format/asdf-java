package org.asdfformat.asdf.metadata;

/**
 * Metadata describing an extension to the ASDF Standard.
 */
public interface Extension {

    /**
     * Get the name of the (Python) class that implements
     * the extension.
     */
    String getExtensionClass();

    /**
     * Get the URI identifying the extension.
     */
    String getExtensionUri();

    /**
     * Get the software package that implements
     * the extension.
     */
    Software getSoftware();
}
