package net.sf.völundr.io;

import java.nio.charset.Charset;

import net.sf.völundr.LineVisitor;

public final class GZipStreamReaderFactory implements StreamReaderFactory {

    private final Charset charset;

    public GZipStreamReaderFactory(final Charset charset) {
        this.charset = charset;
    }

    @Override
    public StreamReader streamReader(final LineVisitor visitor) {
        return new GZipStreamToLines(
                new InputStreamToLines(visitor, charset()));
    }

    private Charset charset() {
        return this.charset;
    }

}
