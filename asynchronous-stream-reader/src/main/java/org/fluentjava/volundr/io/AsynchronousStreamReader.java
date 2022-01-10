package org.fluentjava.volundr.io;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadFactory;

import org.fluentjava.volundr.LineVisitor;
import org.fluentjava.volundr.concurrent.NamedThreadFactory;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class AsynchronousStreamReader {
    private final LineVisitor visitor;
    private final List<Thread> tasks = new ArrayList<>();
    private final ThreadFactory threadFactory;
    private final StreamReadFailedNotifier failNotifier;
    private final StreamReaderFactory streamReaderFactory;

    public AsynchronousStreamReader(final LineVisitor visitor,
            final StreamReaderFactory streamReaderFactory,
            final StreamReadFailedNotifier failNotifier) {
        this.visitor = visitor;
        this.streamReaderFactory = streamReaderFactory;
        this.failNotifier = failNotifier;
        this.threadFactory = NamedThreadFactory
                .forNamePrefix("async-stream-reader-thread-");
    }

    @SuppressWarnings({ "PMD.CloseResource",
            "PMD.AvoidInstantiatingObjectsInLoops",
            "PMD.AvoidCatchingThrowable" })
    public void readFrom(final InputStream... streams) {
        tasks().clear();
        for (final InputStream stream : streams) {
            final Thread thread = threadFactory().newThread(() -> {
                try {
                    log.info("Reading the stream...");
                    streamReaderFactory().streamReader(visitor())
                            .readFrom(stream);
                    log.info("Stream has been read successfully.");
                } catch (Throwable t) {
                    log.error("Reading the stream failed!", t);
                    readFailNotifier().readFailed(stream, t);
                }
            });
            tasks().add(thread);
            thread.start();
        }
    }

    private StreamReadFailedNotifier readFailNotifier() {
        return this.failNotifier;
    }

    private StreamReaderFactory streamReaderFactory() {
        return this.streamReaderFactory;
    }

    private ThreadFactory threadFactory() {
        return this.threadFactory;
    }

    public void waitUntilDone() throws InterruptedException {
        log.info("Waiting until done...");
        for (final Thread thread : tasks()) {
            thread.join();
        }
        log.info("Done.");
        tasks().clear();
    }

    private List<Thread> tasks() {
        return this.tasks;
    }

    private LineVisitor visitor() {
        return this.visitor;
    }

}
