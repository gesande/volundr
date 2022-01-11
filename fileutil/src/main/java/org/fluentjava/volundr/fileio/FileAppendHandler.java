package org.fluentjava.volundr.fileio;

import java.io.IOException;

public interface FileAppendHandler {

    void failed(final String file, final IOException e);

    void ok(final String file);

    void start(String file);

}
