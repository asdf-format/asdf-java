package org.asdfformat.asdf;

import lombok.Builder;
import lombok.Getter;

import java.nio.file.Path;
import java.nio.file.Paths;

@Getter
@Builder(toBuilder = true)
public class AsdfConfig {
    public static final AsdfConfig DEFAULT = AsdfConfig.builder()
            .tempPath(Paths.get(System.getProperty("java.io.tmpdir")))
            .build();

    private final Path tempPath;
}
