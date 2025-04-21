package org.asdfformat.asdf.standard;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.asdfformat.asdf.util.Version;

@RequiredArgsConstructor
public enum AsdfStandardType {
    VERSION_1_2_0(new Version(1, 2, 0)),
    VERSION_1_3_0(new Version(1, 3, 0)),
    VERSION_1_4_0(new Version(1, 4, 0)),
    VERSION_1_5_0(new Version(1, 5, 0)),
    VERSION_1_6_0(new Version(1, 6, 0)),
    ;

    @Getter
    private final Version version;
}
