package org.fluentjava.völundr.io;

import java.nio.charset.Charset;

import org.fluentjava.völundr.LineVisitor;

public final class InputStreamReaderFactory implements StreamReaderFactory {
    private final Charset charset;

    public InputStreamReaderFactory(final Charset charset) {
        this.charset = charset;
    }

    @Override
    public StreamReader streamReader(final LineVisitor visitor) {
        return new InputStreamToLines(visitor, charset());
    }

    private Charset charset() {
        return this.charset;
    }

}
