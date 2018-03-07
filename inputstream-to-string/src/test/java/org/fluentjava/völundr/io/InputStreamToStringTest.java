package org.fluentjava.völundr.io;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

import org.junit.Test;

public class InputStreamToStringTest {

    @Test
    public void streamToString() throws IOException {
        final InputStream inputStream = new ByteArrayInputStream(
                "stuff on input stream\nstuff end"
                        .getBytes(Charset.defaultCharset()));
        assertEquals("stuff on input stream\nstuff end", toString(inputStream));
    }

    private static String toString(final InputStream inputStream)
            throws IOException {
        return InputStreamToString.forDefaultCharset().toString(inputStream);
    }
}
