package net.sf.völundr.io;

import java.nio.charset.Charset;

public final class StringToBytes {
	private final Charset charset;

	public StringToBytes(final Charset charset) {
		this.charset = charset;
	}

	public byte[] convert(final String data) {
		return data.getBytes(charset());
	}

	private Charset charset() {
		return this.charset;
	}

	public static StringToBytes withDefaultCharset() {
		return new StringToBytes(Charset.defaultCharset());
	}
}