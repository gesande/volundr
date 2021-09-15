package org.fluentjava.volundr.io;

import java.io.InputStream;

public interface StringToInputStream {
    InputStream fromString(final String value);
}
