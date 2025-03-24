package org.asdfformat.asdf.util;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Version implements Comparable<Version> {
    private static final Pattern VERSION_PATTERN = Pattern.compile("^([0-9]+)\\.([0-9]+)\\.([0-9]+)$");

    public static Version fromString(final String value) {
        final Matcher matcher = VERSION_PATTERN.matcher(value);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Version must be in x.y.z format");
        }

        final int major = Integer.parseInt(matcher.group(1));
        final int minor = Integer.parseInt(matcher.group(2));
        final int patch = Integer.parseInt(matcher.group(3));

        return new Version(major, minor, patch);
    }

    private final int major;
    private final int minor;
    private final int patch;

    public Version(final int major, final int minor, final int patch) {
        this.major = major;
        this.minor = minor;
        this.patch = patch;
    }

    public int getMajor() {
        return major;
    }

    public int getMinor() {
        return minor;
    }

    public int getPatch() {
        return patch;
    }

    @Override
    public String toString() {
        return String.format("%d.%d.%d", major, minor, patch);
    }

    @Override
    public int compareTo(final Version other) {
        if (major != other.major) {
            return major > other.major ? 1 : -1;
        }

        if (minor != other.minor) {
            return minor > other.minor ? 1 : -1;
        }

        if (patch != other.patch) {
            return patch > other.patch ? 1 : -1;
        }

        return 0;
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        final Version otherVersion = (Version) other;
        return major == otherVersion.major && minor == otherVersion.minor && patch == otherVersion.patch;
    }

    @Override
    public int hashCode() {
        return Objects.hash(major, minor, patch);
    }
}
