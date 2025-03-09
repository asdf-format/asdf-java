package org.asdfformat.asdf.metadata.impl;

import java.util.List;

import org.asdfformat.asdf.metadata.AsdfMetadata;
import org.asdfformat.asdf.metadata.Extension;
import org.asdfformat.asdf.metadata.HistoryEntry;
import org.asdfformat.asdf.metadata.Software;


public class AsdfMetadataImpl implements AsdfMetadata {
    private final String asdfVersion;
    private final String asdfStandardVersion;
    private final Software asdfLibrary;
    private final List<Extension> extensions;
    private final List<HistoryEntry> history;

    public AsdfMetadataImpl(final String asdfVersion, final String asdfStandardVersion, final Software asdfLibrary, final List<Extension> extensions, final List<HistoryEntry> history) {
        this.asdfVersion = asdfVersion;
        this.asdfStandardVersion = asdfStandardVersion;
        this.asdfLibrary = asdfLibrary;
        this.extensions = extensions;
        this.history = history;
    }

    @Override
    public String getAsdfVersion() {
        return asdfVersion;
    }

    @Override
    public String getAsdfStandardVersion() {
        return asdfStandardVersion;
    }

    @Override
    public Software getAsdfLibrary() {
        return asdfLibrary;
    }

    @Override
    public List<Extension> getExtensions() {
        return extensions;
    }

    @Override
    public List<HistoryEntry> getHistory() {
        return history;
    }
}
