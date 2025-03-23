package org.asdfformat.asdf.metadata.impl;

import lombok.RequiredArgsConstructor;
import org.asdfformat.asdf.metadata.HistoryEntry;
import org.asdfformat.asdf.metadata.Software;
import org.asdfformat.asdf.node.AsdfNode;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class HistoryEntry100 implements HistoryEntry {
    public static final String TAG = "tag:stsci.edu:asdf/core/history_entry-1.0.0";

    private final AsdfNode inner;

    @Override
    public String getDescription() {
        return inner.containsKey("description") ? inner.getString("description") : null;
    }

    @Override
    public Instant getTime() {
        return inner.containsKey("time") ? inner.getInstant("time") : null;
    }

    @Override
    public List<Software> getSoftwares() {
        final List<Software> result = new ArrayList<>();

        if (!inner.containsKey("software")) {
            return result;
        }

        final AsdfNode softwareNode = inner.get("software");
        if (softwareNode.isMapping()) {
            result.add(new Software100(softwareNode));
        } else if (softwareNode.isSequence()) {
            for (final AsdfNode softwareElementNode : softwareNode) {
                result.add(new Software100(softwareElementNode));
            }
        }

        return result;
    }

    @Override
    public String toString() {
        return String.format(
                "HistoryEntry(description=%s)",
                getDescription()
        );
    }
}
