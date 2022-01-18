package org.fluentjava.volundr;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;

import org.junit.jupiter.api.Test;

public class StringToOutputStreamTest {

    @Test
    public void write() throws IOException {
        final ByteArrayOutputStream stream = new ByteArrayOutputStream();
        final StringToOutputStream writer = StringToOutputStream
                .usingDefaultCharset(stream);
        final String data = """
                line1
                line2
                line3
                line3
                line4
                """;
        writer.write(data);
        assertEquals(data, stream.toString(Charset.defaultCharset().name()));
    }

    @Test
    public void writeUtf8() throws IOException {
        final ByteArrayOutputStream stream = new ByteArrayOutputStream();
        final StringToOutputStream writer = StringToOutputStream
                .usingUtf8(stream);
        final String data = """
                line1
                line2
                line3
                line3
                line4
                """;
        writer.write(data);
        assertEquals(data, stream.toString(UTF_8));
    }
}
