package org.fluentjava.volundr;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import org.junit.Test;

public class StringToOutputStreamTest {

    @Test
    public void write() throws IOException {
        final ByteArrayOutputStream stream = new ByteArrayOutputStream();
        final StringToOutputStream writer = StringToOutputStream
                .usingDefaultCharset(stream);
        final String data = "line1\n" + "line2\n" + "line3\n" + "line3\n"
                + "line4\n";
        writer.write(data);
        assertEquals(data, stream.toString(Charset.defaultCharset().name()));
    }

    @Test
    public void writeUtf8() throws IOException {
        final ByteArrayOutputStream stream = new ByteArrayOutputStream();
        final StringToOutputStream writer = StringToOutputStream
                .usingUtf8(stream);
        final String data = "line1\n" + "line2\n" + "line3\n" + "line3\n"
                + "line4\n";
        writer.write(data);
        assertEquals(data, stream.toString(StandardCharsets.UTF_8.name()));
    }
}
