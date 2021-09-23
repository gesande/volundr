package org.fluentjava.volundr.io;

import java.io.IOException;
import java.io.InputStream;

public interface StreamReader {

    void readFrom(final InputStream stream) throws IOException;

}
