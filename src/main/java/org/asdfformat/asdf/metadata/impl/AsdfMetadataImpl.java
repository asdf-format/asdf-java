package org.asdfformat.asdf.metadata.impl;

import org.asdfformat.asdf.metadata.AsdfMetadata;
import org.asdfformat.asdf.metadata.Extension;
import org.asdfformat.asdf.metadata.HistoryEntry;
import org.asdfformat.asdf.metadata.Software;
import org.asdfformat.asdf.util.Version;

import java.util.List;


public class AsdfMetadataImpl implements AsdfMetadata {
    private final Version asdfVersion;
    private final Version asdfStandardVersion;
    private final Software asdfLibrary;
    private final List<Extension> extensions;
    private final List<HistoryEntry> history;

    public AsdfMetadataImpl(final Version asdfVersion, final Version asdfStandardVersion, final Software asdfLibrary, final List<Extension> extensions, final List<HistoryEntry> history) {
        this.asdfVersion = asdfVersion;
        this.asdfStandardVersion = asdfStandardVersion;
        this.asdfLibrary = asdfLibrary;
        this.extensions = extensions;
        this.history = history;
    }

    @Override
    public String getAsdfVersion() {
        return asdfVersion.toString();
    }

    @Override
    public String getAsdfStandardVersion() {
        return asdfStandardVersion.toString();
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

    @Override
    public String toString() {
        return String.format(
                "AsdfMetadata(asdfVersion=%s, asdfStandardVersion=%s)",
                asdfVersion,
                asdfStandardVersion
        );
    }
}
