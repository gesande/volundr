package net.sf.völundr.io;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import net.sf.völundr.LineVisitor;

import org.junit.Test;

public class GZipStreamToLinesTest {

	@Test
	public void read() throws IOException {
		final List<String> values = new ArrayList<String>();
		new GZipStreamToLines(new InputStreamToLines(new LineVisitor() {
			@Override
			public void visit(final String line) {
				values.add(line);
			}

			@Override
			public void emptyLine() {
				throw new FailIHave("You shouldn't come here!");
			}
		}, Charset.defaultCharset())).readFrom(gzipStream());
		assertEquals(3, values.size());
		assertTrue(values.contains("line1"));
		assertTrue(values.contains("line2"));
		assertTrue(values.contains("line3"));
		assertEquals("line1", values.get(0));
		assertEquals("line2", values.get(1));
		assertEquals("line3", values.get(2));
	}

	@Test
	public void whenSomethingGoesWrongVisitingEmptyLine() {
		final AtomicBoolean emptyLines = new AtomicBoolean(false);
		final AtomicBoolean failed = new AtomicBoolean(false);
		final GZipStreamToLines reader = new GZipStreamToLines(
				new InputStreamToLines(new LineVisitor() {

					@Override
					public void visit(final String line) {
						throw new FailIHave("Failed to process line:" + line);
					}

					@Override
					public void emptyLine() {
						emptyLines.set(true);
					}
				}, Charset.defaultCharset()));
		try {
			reader.readFrom(gzipStream());
		} catch (Exception ex) {
			failed.set(true);
			assertEquals(FailIHave.class, ex.getClass());
			assertEquals("Failed to process line:line1", ex.getMessage());
		}
		assertFalse(emptyLines.get());
		assertTrue(failed.get());
	}

	private InputStream gzipStream() {
		return this.getClass().getResourceAsStream("/file.gz");
	}
}
