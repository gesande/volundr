package org.fluentjava.völundr.io;

// TODO: refactor, extract test-dependency module
final class FailIHave extends RuntimeException {

    public FailIHave(final String msg) {
        super(msg);
    }
}
