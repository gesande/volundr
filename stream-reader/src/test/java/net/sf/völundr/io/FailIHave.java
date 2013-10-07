package net.sf.völundr.io;

// TODO: refactor, extract test-dependency module
final class FailIHave extends RuntimeException {

	public FailIHave(final String msg) {
		super(msg);
	}
}