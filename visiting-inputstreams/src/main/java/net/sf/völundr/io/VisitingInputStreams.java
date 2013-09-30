package net.sf.völundr.io;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

import net.sf.völundr.LineVisitor;

public final class VisitingInputStreams {

    private final VisitingInputStreamsHandler handler;
    private final Charset charset;
    private final StreamReadFailedNotifier readFailedNotifier;

    public VisitingInputStreams(final VisitingInputStreamsHandler handler,
            final Charset charset,
            final StreamReadFailedNotifier readFailedNotifier) {
        this.handler = handler;
        this.charset = charset;
        this.readFailedNotifier = readFailedNotifier;
    }

    public void readStreams(final LineVisitor visitor,
            final InputStream... streams) {
        try {
            readStreamsAndWaitUntilDone(new AsynchronousStreamReader(visitor,
                    this.charset, this.readFailedNotifier), streams);
        } finally {
            closeStreams(streams);
        }

    }

    private void readStreamsAndWaitUntilDone(
            final AsynchronousStreamReader reader, final InputStream... streams) {
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
