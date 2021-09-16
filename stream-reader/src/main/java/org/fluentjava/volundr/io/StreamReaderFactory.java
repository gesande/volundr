package org.fluentjava.volundr.io;

import org.fluentjava.volundr.LineVisitor;

@FunctionalInterface
public interface StreamReaderFactory {

    StreamReader streamReader(final LineVisitor visitor);

}
