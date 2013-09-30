package net.sf.völundr.io;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.concurrent.atomic.AtomicBoolean;

import net.sf.völundr.LineVisitor;

import org.junit.Test;

public class VisitingInputStreamsTest {

    @SuppressWarnings("static-method")
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
            public void interruptedWhileWaitingUntilDone(InterruptedException e) {
                VisitingInputStreamsHandler.DEFAULT_HANDLER
                        .interruptedWhileWaitingUntilDone(e);
            }

            @Override
            public void closeStreamFailed(IOException e) {
                closeFailed.set(true);
            }
        }, Charset.defaultCharset(), new StreamReadFailedNotifier() {

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

    @SuppressWarnings("static-method")
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
                    Charset.defaultCharset(), new StreamReadFailedNotifier() {

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
        } catch (Exception e) {
            closeFailed.set(true);
            assertEquals("Close failed due to IOException", e.getCause()
                    .getMessage());
        }
        assertTrue(closeFailed.get());
        assertTrue(readFailed.get());
    }
}
