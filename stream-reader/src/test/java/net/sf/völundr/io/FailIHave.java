package net.sf.völundr.io;

final class FailIHave extends RuntimeException {

    public FailIHave(final String msg) {
        super(msg);
    }
}