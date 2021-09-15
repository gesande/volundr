package org.fluentjava.volundr.io;

import org.fluentjava.volundr.LineVisitor;

public interface StreamReaderFactory {

    StreamReader streamReader(final LineVisitor visitor);

}
