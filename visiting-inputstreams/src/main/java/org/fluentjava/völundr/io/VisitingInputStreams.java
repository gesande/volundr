package org.fluentjava.völundr.io;

import java.io.IOException;
import java.io.InputStream;

import org.fluentjava.völundr.LineVisitor;

public final class VisitingInputStreams {

    private final VisitingInputStreamsHandler handler;
    private final StreamReadFailedNotifier readFailedNotifier;
    private final StreamReaderFactory streamReaderFactory;

    public VisitingInputStreams(final VisitingInputStreamsHandler handler,
            final StreamReaderFactory streamReaderFactory,
            final StreamReadFailedNotifier readFailedNotifier) {
        this.handler = handler;
        this.streamReaderFactory = streamReaderFactory;
        this.readFailedNotifier = readFailedNotifier;
    }

    public void readStreams(final LineVisitor visitor,
            final InputStream... streams) {
        try {
            readStreamsAndWaitUntilDone(
                    new AsynchronousStreamReader(visitor,
                            this.streamReaderFactory, this.readFailedNotifier),
                    streams);
        } finally {
            closeStreams(streams);
        }

    }

    private void readStreamsAndWaitUntilDone(
            final AsynchronousStreamReader reader,
            final InputStream... streams) {
        reader.readFrom(streams);
        try {
            reader.waitUntilDone();
        } catch (InterruptedException e) {
            this.handler.interruptedWhileWaitingUntilDone(e);
        }
    }

    private void closeStreams(final InputStream... streams) {
        for (final InputStream stream : streams) {
            closeStream(stream);
        }
    }

    private void closeStream(final InputStream stream) {
        try {
            stream.close();
        } catch (IOException e) {
            this.handler.closeStreamFailed(e);
        }
    }

}
