package org.fluentjava.volundr;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;

import static java.nio.charset.StandardCharsets.UTF_8;

public final class StringToOutputStream {

    private final OutputStream streamToWrite;
    private final Charset charset;

    private StringToOutputStream(final OutputStream streamToWrite,
            Charset charset) {
        this.streamToWrite = streamToWrite;
        this.charset = charset;
    }

    public void write(final String data) throws IOException {
        this.streamToWrite.write(data.getBytes(this.charset));
    }

    public static StringToOutputStream usingUtf8(
            final OutputStream streamToWrite) {
        return forCharset(streamToWrite, UTF_8);
    }

    public static StringToOutputStream usingDefaultCharset(
            final OutputStream streamToWrite) {
        return forCharset(streamToWrite, Charset.defaultCharset());
    }

    public static StringToOutputStream forCharset(
            final OutputStream streamToWrite, final Charset charset) {
        return new StringToOutputStream(streamToWrite, charset);
    }
}
