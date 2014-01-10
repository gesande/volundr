package net.sf.völundr;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import net.sf.völundr.bag.StronglyTypedSortedBag;
import net.sf.völundr.concurrent.NamedThreadFactory;
import net.sf.völundr.io.AsynchronousStreamReader;
import net.sf.völundr.io.BytesToString;
import net.sf.völundr.io.GZipStreamReaderFactory;
import net.sf.völundr.io.GZipStreamToLines;
import net.sf.völundr.io.InputStreamReaderFactory;
import net.sf.völundr.io.InputStreamToLines;
import net.sf.völundr.io.InputStreamToString;
import net.sf.völundr.io.StreamReadFailedNotifier;
import net.sf.völundr.io.StreamReader;
import net.sf.völundr.io.StreamReaderFactory;
import net.sf.völundr.io.StringToBytes;
import net.sf.völundr.io.VisitingInputStreams;
import net.sf.völundr.io.VisitingInputStreamsHandler;

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
		return new VisitingInputStreams(handler, new InputStreamReaderFactory(
				charset()), readFailedNotifier);
	}

	public VisitingInputStreams visitingGzipInputStreams(
			final VisitingInputStreamsHandler handler,
			final StreamReadFailedNotifier readFailedNotifier) {
		return new VisitingInputStreams(handler, new GZipStreamReaderFactory(
				charset()), readFailedNotifier);
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
		for (final InputStream stream : streams) {
			executor.execute(new Runnable() {

				@Override
				public void run() {
					try {
						reader.readFrom(stream);
					} catch (IOException e) {
						throw new RuntimeException(
								"Reading the stats stream failed!", e);
					}
				}
			});
		}
		executor.shutdown();
		try {
			executor.awaitTermination(awaitTermination,
					timeUnitForAwaitTermination);
			executor.shutdownNow();
			executor.awaitTermination(10, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

	private Charset charset() {
		return this.charset;
	}

	private InputStreamToLines newInputStreamToLines(final LineVisitor visitor) {
		return new InputStreamToLines(visitor, charset());
	}
}
