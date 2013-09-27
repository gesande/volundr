package net.sf.völundr;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.junit.Test;

public class StringToOutputStreamTest {

	@SuppressWarnings("static-method")
	@Test
	public void write() throws IOException {
		final OutputStream stream = new ByteArrayOutputStream();
		final StringToOutputStream writer = StringToOutputStream
				.usingDefaultCharset(stream);
		final String data = "line1\n" + "line2\n" + "line3\n" + "line3\n"
				+ "line4\n";
		writer.write(data);
		assertEquals(data, stream.toString());

	}
}
