package net.sf.völundr.io;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.Charset;

final class ToByteArrayStream {
    private Charset charset;

    public ToByteArrayStream(final Charset charset) {
        this.charset = charset;
    }

    public InputStream toByteArrayStream(final String value) {
        return new ByteArrayInputStream(value.getBytes(this.charset));
    }
}