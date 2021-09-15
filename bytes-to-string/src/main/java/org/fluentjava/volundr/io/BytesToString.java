package org.fluentjava.volundr.io;

import java.nio.charset.Charset;

public final class BytesToString {

    private final Charset charset;

    private BytesToString(final Charset charset) {
        this.charset = charset;
    }

    public String convert(final byte[] bytes) {
        return new String(bytes, charset());
    }

    private Charset charset() {
        return this.charset;
    }

    public static BytesToString forCharset(final Charset charset) {
        return new BytesToString(charset);
    }

}
