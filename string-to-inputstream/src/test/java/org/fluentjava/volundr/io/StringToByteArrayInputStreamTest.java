package org.fluentjava.volundr.io;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

import org.fluentjava.volundr.LineReader;
import org.fluentjava.volundr.LineVisitor;
import org.junit.Test;

public class StringToByteArrayInputStreamTest {

    @Test
    public void fromString() throws IOException {
        final InputStream fromString = new StringToByteArrayInputStream(
                StringToBytes.withDefaultCharset()).fromString("value");
        new LineReader(Charset.defaultCharset()).read(fromString,
                new LineVisitor() {

                    @Override
                    public void visit(String line) {
                        assertEquals("value", line);
                    }

                    @Override
                    public void emptyLine() {
                        fail("no empty lines expected");
                    }
                });
    }
}
