package net.sf.völundr.io;

import java.io.IOException;
import java.io.InputStream;

public interface StreamReader {

    void readFrom(final InputStream stream) throws IOException;

}
