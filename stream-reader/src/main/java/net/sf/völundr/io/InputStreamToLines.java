package net.sf.völundr.io;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

import net.sf.völundr.LineReader;
import net.sf.völundr.LineVisitor;

public final class InputStreamToLines implements StreamReader {
    private final LineVisitor visitor;
    private final LineReader lineReader;

    public InputStreamToLines(final LineVisitor visitor,
            final Charset charset) {
        this.visitor = visitor;
        this.lineReader = new LineReader(charset);
    }

    @Override
    public void readFrom(final InputStream stream) throws IOException {
        this.lineReader.read(stream, this.visitor);
    }
}
