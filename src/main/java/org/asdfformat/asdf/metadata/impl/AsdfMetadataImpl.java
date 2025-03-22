package org.asdfformat.asdf.metadata.impl;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.asdfformat.asdf.metadata.AsdfMetadata;
import org.asdfformat.asdf.metadata.Extension;
import org.asdfformat.asdf.metadata.HistoryEntry;
import org.asdfformat.asdf.metadata.Software;
import org.asdfformat.asdf.util.Version;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class AsdfMetadataImpl implements AsdfMetadata {
    private final Version asdfVersion;
    private final Version asdfStandardVersion;
    private final Software asdfLibrary;
    private final List<Extension> extensions;
    private final List<HistoryEntry> history;

    @Override
    public String toString() {
        return String.format(
                "AsdfMetadata(asdfVersion=%s, asdfStandardVersion=%s)",
                asdfVersion,
                asdfStandardVersion
        );
    }
}
