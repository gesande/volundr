package org.fluentjava.volundr.fileio;

public interface FileAppendHandler {

    void failed(final String file, final AppendToFileFailed e);

    void ok(final String file);

    void start(String file);

}
