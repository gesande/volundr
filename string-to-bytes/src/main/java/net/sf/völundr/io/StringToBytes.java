package net.sf.völundr.io;

import java.nio.charset.Charset;

public final class StringToBytes {
	private final Charset charset;

	private StringToBytes(final Charset charset) {
		this.charset = charset;
	}

	public byte[] convert(final String data) {
		return data.getBytes(charset());
	}

	private Charset charset() {
		return this.charset;
	}

	public static StringToBytes withDefaultCharset() {
		return forCharset(Charset.defaultCharset());
	}

	public static StringToBytes forCharset(final Charset charset) {
		return new StringToBytes(charset);
	}
}