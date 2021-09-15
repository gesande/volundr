package org.fluentjava.volundr.io;

import static org.junit.Assert.assertTrue;

import java.nio.charset.Charset;
import java.util.Arrays;

import org.junit.Test;

public class StringToBytesTest {

    @Test
    public void convertToBytes() {
        assertTrue(Arrays.equals(
                println(new byte[] { 118, -61, -74, 108, 117, 110, 100, 114 }),
                println(StringToBytes.forCharset(Charset.forName("UTF-8"))
                        .convert("völundr"))));
    }

    @Test
    public void convertToBytesDefaultCharset() {
        assertTrue(Arrays.equals(
                println(new byte[] { 118, -61, -74, 108, 117, 110, 100, 114 }),
                println(StringToBytes.withDefaultCharset()
                        .convert("völundr"))));
    }

    private static byte[] println(byte[] a) {
        System.out.println(Arrays.toString(a));
        return a;
    }
}
