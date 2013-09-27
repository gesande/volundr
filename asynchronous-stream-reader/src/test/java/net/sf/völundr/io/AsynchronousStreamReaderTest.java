package net.sf.völundr.io;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import net.sf.völundr.LineVisitor;

import org.junit.Test;

public class AsynchronousStreamReaderTest {
	@SuppressWarnings("static-method")
	@Test
	public void readFiles() throws InterruptedException {
		final InputStream stream1 = new ByteArrayInputStream(
				"line1\n".getBytes());
		final InputStream stream2 = new ByteArrayInputStream(
				"line2\n\nline3".getBytes());
		final InputStream stream3 = new ByteArrayInputStream(
				"line4\n".getBytes());

		final AtomicBoolean emptyLines = new AtomicBoolean(false);
		final List<String> visited = Collections
				.synchronizedList(new ArrayList<String>(905));

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
		final AtomicBoolean failed = new AtomicBoolean(false);
		final AsynchronousStreamReader multipleStreamReader = new AsynchronousStreamReader(
				visitor, Charset.defaultCharset(),
				new StreamReadFailedNotifier() {

					@Override
					public void readFailed(final InputStream stream,
							final Throwable t) {
						failed.set(true);
					}
				});
		try {
			multipleStreamReader.readFrom(stream1, stream2, stream3);
			multipleStreamReader.waitUntilDone();
		} finally {
			closeStream(stream1);
			closeStream(stream2);
			closeStream(stream3);
		}
		assertFalse(failed.get());
		assertTrue(emptyLines.get());
		assertEquals(4, visited.size());
	}

	private static void closeStream(final InputStream stream) {
		try {
			stream.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@SuppressWarnings("static-method")
	@Test
	public void readFailed() throws InterruptedException {
		final InputStream stream1 = new ByteArrayInputStream(
				"line1\n".getBytes());
		final FailIHave failIHave = new FailIHave();
		final AtomicBoolean failed = new AtomicBoolean(false);
		final AsynchronousStreamReader multipleStreamReader = new AsynchronousStreamReader(
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
				}, Charset.defaultCharset(), new StreamReadFailedNotifier() {

					@Override
					public void readFailed(InputStream stream, Throwable t) {
						assertEquals(stream1, stream);
						assertEquals(failIHave, t);
						failed.set(true);
					}
				});
		multipleStreamReader.readFrom(stream1);
		multipleStreamReader.waitUntilDone();
		assertTrue(failed.get());
	}

	private final static class FailIHave extends RuntimeException {

		public FailIHave() {
		}

	}
}
