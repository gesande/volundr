package org.fluentjava.volundr.io;

import java.io.IOException;
import java.io.InputStream;
import java.util.zip.GZIPInputStream;

public class GZipStreamToLines implements StreamReader {
    private final StreamReader reader;

    public GZipStreamToLines(final StreamReader reader) {
        this.reader = reader;
    }

    @Override
    public void readFrom(final InputStream inputStream) throws IOException {
        try (GZIPInputStream gzipStream = new GZIPInputStream(inputStream)) {
            this.reader.readFrom(gzipStream);
        }
    }

}
