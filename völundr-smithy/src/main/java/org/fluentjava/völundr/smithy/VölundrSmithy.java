package org.fluentjava.völundr.smithy;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.fluentjava.völundr.LineReader;
import org.fluentjava.völundr.LineVisitor;
import org.fluentjava.völundr.StringToOutputStream;
import org.fluentjava.völundr.bag.StronglyTypedSortedBag;
import org.fluentjava.völundr.concurrent.NamedThreadFactory;
import org.fluentjava.völundr.io.AsynchronousStreamReader;
import org.fluentjava.völundr.io.BytesToString;
import org.fluentjava.völundr.io.GZipStreamReaderFactory;
import org.fluentjava.völundr.io.GZipStreamToLines;
import org.fluentjava.völundr.io.InputStreamReaderFactory;
import org.fluentjava.völundr.io.InputStreamToLines;
import org.fluentjava.völundr.io.InputStreamToString;
import org.fluentjava.völundr.io.StreamReadFailedNotifier;
import org.fluentjava.völundr.io.StreamReader;
import org.fluentjava.völundr.io.StreamReaderFactory;
import org.fluentjava.völundr.io.StringToBytes;
import org.fluentjava.völundr.io.VisitingInputStreams;
import org.fluentjava.völundr.io.VisitingInputStreamsHandler;

public final class VölundrSmithy {

    private final Charset charset;

    public VölundrSmithy(final Charset charset) {
        this.charset = charset;
    }

    public LineReader lineReader() {
        return new LineReader(charset());
    }

    public StreamReader inputStreamToLines(final LineVisitor visitor) {
        return newInputStreamToLines(visitor);
    }

    public StreamReader gzipInputStreamToLines(final LineVisitor visitor) {
        return new GZipStreamToLines(newInputStreamToLines(visitor));
    }

    @SuppressWarnings("static-method")
    public AsynchronousStreamReader asynchronousStreamReader(
            final LineVisitor visitor,
            final StreamReadFailedNotifier failNotifier,
            final StreamReaderFactory streamReaderFactory) {
        return new AsynchronousStreamReader(visitor, streamReaderFactory,
                failNotifier);
    }

    public StringToOutputStream stringToOutputStream(
            final OutputStream streamToWrite) {
        return StringToOutputStream.forCharset(streamToWrite, charset());
    }

    public InputStreamToString inputStreamToString() {
        return InputStreamToString.forCharset(charset());
    }

    public VisitingInputStreams visitingInputStreams(
            final VisitingInputStreamsHandler handler,
            final StreamReadFailedNotifier readFailedNotifier) {
        return new VisitingInputStreams(handler,
                new InputStreamReaderFactory(charset()), readFailedNotifier);
    }

    public VisitingInputStreams visitingGzipInputStreams(
            final VisitingInputStreamsHandler handler,
            final StreamReadFailedNotifier readFailedNotifier) {
        return new VisitingInputStreams(handler,
                new GZipStreamReaderFactory(charset()), readFailedNotifier);
    }

    public StringToBytes stringToBytes() {
        return StringToBytes.forCharset(charset());
    }

    public BytesToString bytesToString() {
        return BytesToString.forCharset(charset());
    }

    @SuppressWarnings("static-method")
    public <TYPE> StronglyTypedSortedBag<TYPE> synchronizedTreeBag() {
        return StronglyTypedSortedBag.synchronizedTreeBag();
    }

    @SuppressWarnings("static-method")
    public <TYPE> StronglyTypedSortedBag<TYPE> treeBag() {
        return StronglyTypedSortedBag.treeBag();
    }

    @SuppressWarnings("static-method")
    public void readStreamsWith(final int threads, final int awaitTermination,
            final TimeUnit timeUnitForAwaitTermination,
            final StreamReader reader, final InputStream... streams) {
        final ExecutorService executor = Executors.newFixedThreadPool(threads,
                NamedThreadFactory.forNamePrefix("stream-reader-"));
        final CountDownLatch latch = new CountDownLatch(streams.length);
        for (final InputStream stream : streams) {
            executor.execute(new Runnable() {

                @Override
                public void run() {
                    try {
                        reader.readFrom(stream);
                    } catch (IOException e) {
                        throw new RuntimeException(
                                "Reading the stats stream failed!", e);
                    } finally {
                        latch.countDown();
                    }
                }
            });
        }
        shutdown(awaitTermination, timeUnitForAwaitTermination, executor);
    }

    private static void shutdown(final int awaitTermination,
            final TimeUnit timeUnitForAwaitTermination,
            final ExecutorService executor) {
        executor.shutdown();
        try {
            executor.awaitTermination(awaitTermination,
                    timeUnitForAwaitTermination);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private Charset charset() {
        return this.charset;
    }

    private InputStreamToLines newInputStreamToLines(
            final LineVisitor visitor) {
        return new InputStreamToLines(visitor, charset());
    }
}
