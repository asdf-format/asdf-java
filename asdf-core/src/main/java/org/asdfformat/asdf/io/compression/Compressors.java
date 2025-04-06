package org.asdfformat.asdf.io.compression;

import lombok.Builder;
import lombok.Getter;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Compressors {
    private static final OptionalCompressor[] OPTIONAL_COMPRESSORS = {
            OptionalCompressor.builder()
                    .className("org.asdfformat.asdf.io.compression.BloscCompressor")
                    .identifier("blsc".getBytes(StandardCharsets.UTF_8))
                    .moduleName("asdf-compression-blosc")
                    .build(),
            OptionalCompressor.builder()
                    .className("org.asdfformat.asdf.io.compression.BZip2Compressor")
                    .identifier("bzp2".getBytes(StandardCharsets.UTF_8))
                    .moduleName("asdf-compression-commons-compress")
                    .build(),
            OptionalCompressor.builder()
                    .className("org.asdfformat.asdf.io.compression.Lz4FrameCompressor")
                    .identifier("lz4f".getBytes(StandardCharsets.UTF_8))
                    .moduleName("asdf-compression-commons-compress")
                    .build(),
            OptionalCompressor.builder()
                    .className("org.asdfformat.asdf.io.compression.ZStandardCompressor")
                    .identifier("zstd".getBytes(StandardCharsets.UTF_8))
                    .moduleName("asdf-compression-zstd")
                    .build()
    };

    private static final Map<ByteBuffer, Compressor> COMPRESSORS = new HashMap<>();
    static {
        final ZlibCompressor zlibCompressor = new ZlibCompressor();
        COMPRESSORS.put(ByteBuffer.wrap(zlibCompressor.getIdentifier()), zlibCompressor);

        for (final OptionalCompressor optionalCompressor: OPTIONAL_COMPRESSORS) {
            createOptionalCompressor(optionalCompressor).ifPresent(c -> COMPRESSORS.put(ByteBuffer.wrap(c.getIdentifier()), c));
        }
    }

    private static Optional<Compressor> createOptionalCompressor(final OptionalCompressor optionalCompressor) {
        try {
            final Class<?> clazz = Class.forName(optionalCompressor.getClassName());
            return Optional.of((Compressor) clazz.getConstructor().newInstance());
        } catch (final NoClassDefFoundError | ReflectiveOperationException e) {
            return Optional.empty();
        }
    }

    public static Compressor of(final byte[] identifier) {
        final Compressor compressor = COMPRESSORS.get(ByteBuffer.wrap(identifier));
        if (compressor == null) {
            final OptionalCompressor optionalCompressor = Arrays.stream(OPTIONAL_COMPRESSORS)
                    .filter(o -> Arrays.equals(o.getIdentifier(), identifier))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException(String.format("Unrecognized compression identifier '%s'", new String(identifier, StandardCharsets.UTF_8))));

            throw new RuntimeException(String.format(
                    "Install the %s module for '%s' compression support",
                    optionalCompressor.getModuleName(),
                    new String(identifier, StandardCharsets.UTF_8))
            );
        }
        return compressor;
    }

    @Getter
    @Builder
    private static class OptionalCompressor {
        private final String className;
        private final byte[] identifier;
        private final String moduleName;
    }
}
