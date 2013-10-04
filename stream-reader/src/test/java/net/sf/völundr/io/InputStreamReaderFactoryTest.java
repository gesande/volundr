package net.sf.völundr.io;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.concurrent.atomic.AtomicInteger;

import net.sf.völundr.LineVisitor;

import org.junit.Test;

public class InputStreamReaderFactoryTest {

    @SuppressWarnings("static-method")
    @Test
    public void reader() throws IOException {
        final Charset charset = Charset.defaultCharset();
        final InputStream stream = new ToByteArrayStream(charset)
                .toByteArrayStream("line1\n\nline3");
        final AtomicInteger lines = new AtomicInteger();
        final AtomicInteger emptyLines = new AtomicInteger();
        new InputStreamReaderFactory(charset).streamReader(new LineVisitor() {

            @Override
            public void visit(String line) {
                lines.incrementAndGet();
            }

            @Override
            public void emptyLine() {
                emptyLines.incrementAndGet();
            }
        }).readFrom(stream);
        assertEquals(2, lines.get());
        assertEquals(1, emptyLines.get());
    }
}
