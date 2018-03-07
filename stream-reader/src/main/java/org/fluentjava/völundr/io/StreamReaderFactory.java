package org.fluentjava.völundr.io;

import org.fluentjava.völundr.LineVisitor;

public interface StreamReaderFactory {

    StreamReader streamReader(final LineVisitor visitor);

}
