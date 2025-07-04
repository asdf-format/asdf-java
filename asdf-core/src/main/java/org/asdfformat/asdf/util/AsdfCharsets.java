package org.asdfformat.asdf.util;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class AsdfCharsets {
    public static final Charset ASCII = StandardCharsets.US_ASCII;
    public static final Charset UTF_32BE = Charset.forName("UTF_32BE");
    public static final Charset UTF_32LE = Charset.forName("UTF_32LE");
}
