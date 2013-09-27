package net.sf.völundr.io;

import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadFactory;

import net.sf.völundr.LineVisitor;
import net.sf.völundr.concurrent.NamedThreadFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AsynchronousStreamReader {
	private final static Logger LOGGER = LoggerFactory
			.getLogger(AsynchronousStreamReader.class);
	private final LineVisitor visitor;
	private final List<Thread> tasks = new ArrayList<Thread>();
	private final ThreadFactory threadFactory;
	private final Charset charset;
	private StreamReadFailedNotifier failNotifier;

	public AsynchronousStreamReader(final LineVisitor visitor,
			final Charset charset, final StreamReadFailedNotifier failNotifier) {
		this.visitor = visitor;
		this.failNotifier = failNotifier;
		this.threadFactory = NamedThreadFactory
				.forNamePrefix("async-stream-reader-thread-");
		this.charset = charset;
	}

	public void readFrom(final InputStream... streams) {
		tasks().clear();
		for (final InputStream stream : streams) {
			final Thread thread = threadFactory().newThread(new Runnable() {
				@Override
				public void run() {
					try {
						LOGGER.info("Reading the stream...");
						new StreamReader(visitor(), charset()).readFrom(stream);
						LOGGER.info("Stream has been read successfully.");
					} catch (Throwable t) {
						LOGGER.error("Reading the stream failed!", t);
						readFailNotifier().readFailed(stream, t);
					}
				}

			});
			tasks().add(thread);
			thread.start();
		}
	}

	private StreamReadFailedNotifier readFailNotifier() {
		return this.failNotifier;
	}

	private Charset charset() {
		return this.charset;
	}

	private ThreadFactory threadFactory() {
		return this.threadFactory;
	}

	public void waitUntilDone() throws InterruptedException {
		LOGGER.info("Waiting until done...");
		for (final Thread thread : tasks()) {
			thread.join();
		}
		LOGGER.info("Done.");
		tasks().clear();
	}

	private List<Thread> tasks() {
		return this.tasks;
	}

	private LineVisitor visitor() {
		return this.visitor;
	}

}
