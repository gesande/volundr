package org.fluentjava.völundr.io;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

import org.apache.commons.io.IOUtils;

public final class InputStreamToString {

    private Charset charset;

    private InputStreamToString(final Charset charset) {
        this.charset = charset;
    }

    public String toString(final InputStream inputStream) throws IOException {
        return new String(IOUtils.toByteArray(inputStream), this.charset);
    }

    public static InputStreamToString forDefaultCharset() {
        return forCharset(Charset.defaultCharset());
    }

    public static InputStreamToString forCharset(final Charset charset) {
        return new InputStreamToString(charset);
    }
}
