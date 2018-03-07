package org.fluentjava.völundr.io;

import static org.junit.Assert.assertEquals;

import java.nio.charset.Charset;

import org.junit.Test;

public class BytesToStringTest {

    @Test
    public void fromBytes() {
        assertEquals("völundr",
                BytesToString.forCharset(Charset.forName("UTF-8")).convert(
                        new byte[] { 118, -61, -74, 108, 117, 110, 100, 114 }));

    }
}
