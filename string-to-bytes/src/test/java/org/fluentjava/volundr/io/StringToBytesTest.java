package org.fluentjava.volundr.io;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.junit.Assert.assertArrayEquals;

import java.util.Arrays;

import org.junit.Test;

public class StringToBytesTest {

    @Test
    public void convertToBytes() {
        assertArrayEquals(
                println(new byte[] { 118, -61, -74, 108, 117, 110, 100, 114 }),
                println(StringToBytes.forCharset(UTF_8).convert("völundr")));
    }

    @Test
    public void convertToBytesDefaultCharset() {
        assertArrayEquals(
                println(new byte[] { 118, -61, -74, 108, 117, 110, 100, 114 }),
                println(StringToBytes.withDefaultCharset().convert("völundr")));
    }

    @SuppressWarnings("PMD.SystemPrintln")
    private static byte[] println(byte[] a) {
        System.out.println(Arrays.toString(a));
        return a;
    }
}
