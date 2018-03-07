package org.fluentjava.völundr.io;

import java.io.InputStream;

public interface StreamReadFailedNotifier {

    void readFailed(InputStream stream, Throwable t);

}
