package org.fluentjava.volundr.smithy;

public class VolundrSmithyException extends RuntimeException {
    public VolundrSmithyException(Throwable cause) {
        super(cause);
    }

    public VolundrSmithyException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
