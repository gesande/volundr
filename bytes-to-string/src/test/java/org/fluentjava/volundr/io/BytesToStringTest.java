package org.fluentjava.volundr.io;

import org.junit.Test;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.junit.Assert.assertEquals;

public class BytesToStringTest {

    @Test
    public void fromBytes() {
        assertEquals("völundr",
                BytesToString.forCharset(UTF_8).convert(
                        new byte[] { 118, -61, -74, 108, 117, 110, 100, 114 }));

    }
}
