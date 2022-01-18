package org.fluentjava.volundr;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import org.junit.jupiter.api.Test;

public class LineReaderTest {

    @Test
    public void noEmptyLines() throws IOException {
        final AtomicBoolean emptyLineDetected;
        final List<String> lines;
        try (InputStream stream = inputStreamWith3Lines()) {
            emptyLineDetected = new AtomicBoolean(false);
            lines = new ArrayList<>(3);
            new LineReader(Charset.defaultCharset()).read(stream,
                    new LineVisitor() {

                        @Override
                        public void visit(final String line) {
                            lines.add(line);
                        }

                        @Override
                        public void emptyLine() {
                            emptyLineDetected.getAndSet(true);
                        }
                    });
        }
        assertEquals(3, lines.size());
        assertFalse(emptyLineDetected.get());
        assertEquals("line1", lines.get(0));
        assertEquals("line2", lines.get(1));
        assertEquals("line3", lines.get(2));
    }

    @Test
    public void emptyLines() throws IOException {
        final AtomicBoolean emptyLineDetected;
        final List<String> lines;
        try (InputStream byteArrayInputStream = contentsWithEmptyLine()) {
            emptyLineDetected = new AtomicBoolean(false);
            lines = new ArrayList<>(3);
            new LineReader(Charset.defaultCharset()).read(byteArrayInputStream,
                    new LineVisitor() {

                        @Override
                        public void visit(final String line) {
                            lines.add(line);
                        }

                        @Override
                        public void emptyLine() {
                            lines.add("");
                            emptyLineDetected.getAndSet(true);
                        }
                    });
        }
        assertEquals(3, lines.size());
        assertTrue(emptyLineDetected.get());
        assertEquals("line1", lines.get(0));
        assertEquals("", lines.get(1));
        assertEquals("line3", lines.get(2));
    }

    @Test
    public void exceptionDuringVisit() {
        assertThrows(FailIHave.class,
                () -> new LineReader(Charset.defaultCharset())
                        .read(inputStreamWith3Lines(), new LineVisitor() {

                            @Override
                            public void visit(final String line) {
                                throw new FailIHave();
                            }

                            @Override
                            public void emptyLine() {//
                            }
                        }));
    }

    @Test
    public void exceptionDuringEmptyLine() {
        assertThrows(FailIHave.class,
                () -> new LineReader(Charset.defaultCharset())
                        .read(contentsWithEmptyLine(), new LineVisitor() {

                            @Override
                            public void visit(final String line) {//
                            }

                            @Override
                            public void emptyLine() {
                                throw new FailIHave();
                            }
                        }));
    }

    private final static class FailIHave extends RuntimeException {//

    }

    private static InputStream contentsWithEmptyLine() {
        return newByteArrayInputStream(new StringBuilder().append("line1\n")
                .append("\n").append("line3"));
    }

    private static InputStream inputStreamWith3Lines() {
        return newByteArrayInputStream(new StringBuilder().append("line1\n")
                .append("line2\n").append("line3\n"));
    }

    private static InputStream newByteArrayInputStream(final StringBuilder sb) {
        return new ByteArrayInputStream(
                sb.toString().getBytes(Charset.defaultCharset()));
    }
}
