package org.fluentjava.völundr.io;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import org.fluentjava.völundr.LineVisitor;
import org.junit.Test;

public class VisitingInputStreamsTest {

    @Test
    public void visitStreams() {
        final Charset charset = Charset.forName("UTF-8");
        final List<String> lines = new ArrayList<>();
        final InputStream stream1 = new ByteArrayInputStream(
                "1first line\n1second line\n".getBytes(charset));
        final InputStream stream2 = new ByteArrayInputStream(
                "2first line\n2second line\n2third line".getBytes(charset));
        new VisitingInputStreams(VisitingInputStreamsHandler.DEFAULT_HANDLER,
                new InputStreamReaderFactory(charset),
                new StreamReadFailedNotifier() {

                    @Override
                    public void readFailed(final InputStream stream,
                            final Throwable t) {
                        throw new RuntimeException(t);
                    }
                }).readStreams(new LineVisitor() {

                    @Override
                    public void visit(String line) {
                        synchronized (lines) {
                            lines.add(line);
                        }
                    }

                    @Override
                    public void emptyLine() {
                        throw new RuntimeException("no empty lines expected");
                    }
                }, stream1, stream2);
        assertEquals(5, lines.size());
        assertTrue(lines.contains("1first line"));
        assertTrue(lines.contains("1second line"));
        assertTrue(lines.contains("2first line"));
        assertTrue(lines.contains("2second line"));
        assertTrue(lines.contains("2third line"));
    }

    @Test
    public void closeFailed() {
        final AtomicBoolean closeFailed = new AtomicBoolean(false);
        final InputStream closeFailedStream = new InputStream() {

            @Override
            public int read() throws IOException {
                return -1;
            }

            @Override
            public void close() throws IOException {
                throw new IOException("Close failed");
            }
        };
        final AtomicBoolean readFailed = new AtomicBoolean(false);
        new VisitingInputStreams(new VisitingInputStreamsHandler() {

            @Override
            public void interruptedWhileWaitingUntilDone(
                    InterruptedException e) {
                VisitingInputStreamsHandler.DEFAULT_HANDLER
                        .interruptedWhileWaitingUntilDone(e);
            }

            @Override
            public void closeStreamFailed(IOException e) {
                closeFailed.set(true);
            }
        }, new InputStreamReaderFactory(Charset.defaultCharset()),
                new StreamReadFailedNotifier() {

                    @Override
                    public void readFailed(InputStream stream, Throwable t) {
                        readFailed.set(true);
                        assertEquals(closeFailedStream, stream);
                    }
                }).readStreams(new LineVisitor() {

                    @Override
                    public void visit(String line) {
                        throw new RuntimeException("Should not have come here");
                    }

                    @Override
                    public void emptyLine() {
                        throw new RuntimeException("Should not have come here");
                    }
                }, closeFailedStream);
        assertTrue(closeFailed.get());
        assertTrue(readFailed.get());
    }

    @Test
    public void closeStreamFailedGetsCalled() {
        final AtomicBoolean closeFailed = new AtomicBoolean(false);
        final InputStream closeFailedStream = new InputStream() {

            @Override
            public int read() throws IOException {
                return -1;
            }

            @Override
            public void close() throws IOException {
                throw new IOException("Close failed due to IOException");
            }
        };
        final AtomicBoolean readFailed = new AtomicBoolean(false);
        try {
            new VisitingInputStreams(
                    VisitingInputStreamsHandler.DEFAULT_HANDLER,
                    new InputStreamReaderFactory(Charset.defaultCharset()),
                    new StreamReadFailedNotifier() {

                        @Override
                        public void readFailed(InputStream stream,
                                Throwable t) {
                            readFailed.set(true);
                            assertEquals(closeFailedStream, stream);
                        }
                    }).readStreams(new LineVisitor() {

                        @Override
                        public void visit(String line) {
                            throw new RuntimeException(
                                    "Should not have come here");
                        }

                        @Override
                        public void emptyLine() {
                            throw new RuntimeException(
                                    "Should not have come here");
                        }
                    }, closeFailedStream);
        } catch (Exception e) {
            closeFailed.set(true);
            assertEquals("Close failed due to IOException",
                    e.getCause().getMessage());
        }
        assertTrue(closeFailed.get());
        assertTrue(readFailed.get());
    }
}
