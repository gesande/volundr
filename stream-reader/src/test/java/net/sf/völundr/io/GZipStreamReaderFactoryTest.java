package net.sf.völundr.io;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Test;

import net.sf.völundr.LineVisitor;

public class GZipStreamReaderFactoryTest {

	@Test
	public void reader() throws IOException {
		final Charset charset = Charset.defaultCharset();
		final AtomicInteger lines = new AtomicInteger();
		final AtomicInteger emptyLines = new AtomicInteger();
		new GZipStreamReaderFactory(charset).streamReader(new LineVisitor() {

			@Override
			public void visit(String line) {
				lines.incrementAndGet();
			}

			@Override
			public void emptyLine() {
				emptyLines.incrementAndGet();
			}
		}).readFrom(gzipStream());
		assertEquals(2, lines.get());
		assertEquals(1, emptyLines.get());
	}

	private InputStream gzipStream() {
		return this.getClass().getResourceAsStream("/emptylines.gz");
	}

}
