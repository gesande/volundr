package org.fluentjava.volundr.io;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.concurrent.atomic.AtomicInteger;

import org.fluentjava.volundr.LineVisitor;
import org.junit.jupiter.api.Test;

public class InputStreamReaderFactoryTest {

    @Test
    public void reader() throws IOException {
        final Charset charset = Charset.defaultCharset();
        final AtomicInteger lines = new AtomicInteger();
        final AtomicInteger emptyLines = new AtomicInteger();
        try (InputStream stream = new StringToByteArrayInputStream(
                StringToBytes.forCharset(charset))
                        .fromString("line1\n\nline3")) {
            new InputStreamReaderFactory(charset)
                    .streamReader(new LineVisitor() {

                        @Override
                        public void visit(String line) {
                            lines.incrementAndGet();
                        }

                        @Override
                        public void emptyLine() {
                            emptyLines.incrementAndGet();
                        }
                    }).readFrom(stream);
        }
        assertEquals(2, lines.get());
        assertEquals(1, emptyLines.get());
    }
}
