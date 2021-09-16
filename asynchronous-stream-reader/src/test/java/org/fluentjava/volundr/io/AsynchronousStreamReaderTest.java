package org.fluentjava.volundr.io;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import org.fluentjava.volundr.LineVisitor;
import org.junit.Test;

public class AsynchronousStreamReaderTest {

    @Test
    public void readFiles() throws InterruptedException, IOException {
        final AtomicBoolean emptyLines;
        final List<String> visited;
        final AtomicBoolean failed;
        try (InputStream stream1 = toInputStream("line1\n")) {
            try (InputStream stream2 = toInputStream("line2\n\nline3")) {
                try (InputStream stream3 = toInputStream("line4\n")) {

                    emptyLines = new AtomicBoolean(false);
                    visited = Collections
                            .synchronizedList(new ArrayList<>(905));

                    final LineVisitor visitor = new LineVisitor() {
                        @Override
                        public void visit(String line) {
                            visited.add(line);
                        }

                        @Override
                        public void emptyLine() {
                            emptyLines.set(true);
                        }
                    };
                    failed = new AtomicBoolean(false);
                    final AsynchronousStreamReader multipleStreamReader = new AsynchronousStreamReader(
                            visitor,
                            new InputStreamReaderFactory(
                                    Charset.defaultCharset()),
                            (stream, t) -> failed.set(true));
                    multipleStreamReader.readFrom(stream1, stream2, stream3);
                    multipleStreamReader.waitUntilDone();
                }
            }
        }
        assertFalse(failed.get());
        assertTrue(emptyLines.get());
        assertEquals(4, visited.size());
    }

    @Test
    public void readFailed() throws InterruptedException, IOException {
        final AtomicBoolean failed;
        final AsynchronousStreamReader multipleStreamReader;
        try (InputStream stream1 = toInputStream("line1\n")) {
            final FailIHave failIHave = new FailIHave();
            failed = new AtomicBoolean(false);
            multipleStreamReader = new AsynchronousStreamReader(
                    new LineVisitor() {

                        @Override
                        public void visit(String line) {
                            assertEquals("line1", line);
                            throw failIHave;
                        }

                        @Override
                        public void emptyLine() {
                            //
                        }
                    }, new InputStreamReaderFactory(Charset.defaultCharset()),
                    (stream, t) -> {
                        assertEquals(stream1, stream);
                        assertEquals(failIHave, t);
                        failed.set(true);
                    });
            multipleStreamReader.readFrom(stream1);
        }
        multipleStreamReader.waitUntilDone();
        assertTrue(failed.get());
    }

    private static InputStream toInputStream(final String string) {
        return new StringToByteArrayInputStream(
                StringToBytes.forCharset(Charset.defaultCharset()))
                        .fromString(string);
    }

    private final static class FailIHave extends RuntimeException {
    }
}
