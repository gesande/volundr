package net.sf.völundr.io;

import net.sf.völundr.LineVisitor;

public interface StreamReaderFactory {

	StreamReader streamReader(final LineVisitor visitor);

}
