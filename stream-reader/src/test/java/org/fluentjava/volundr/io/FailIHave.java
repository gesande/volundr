package org.fluentjava.volundr.io;

// TODO: refactor, extract test-dependency module
final class FailIHave extends RuntimeException {

    public FailIHave(final String msg) {
        super(msg);
    }
}
