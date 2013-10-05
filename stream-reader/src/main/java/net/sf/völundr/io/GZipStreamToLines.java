package net.sf.völundr.io;

import java.io.IOException;
import java.io.InputStream;
import java.util.zip.GZIPInputStream;

public class GZipStreamToLines implements StreamReader {
	private final InputStreamToLines reader;

	public GZipStreamToLines(final InputStreamToLines reader) {
		this.reader = reader;
	}

	@Override
	public void readFrom(final InputStream inputStream) throws IOException {
		final GZIPInputStream gzipStream = new GZIPInputStream(inputStream);
		try {
			this.reader.readFrom(gzipStream);
		} finally {
			gzipStream.close();
		}
	}

}
