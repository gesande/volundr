package net.sf.völundr.io;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.Test;

public class StringToBytesTest {

    @SuppressWarnings("static-method")
    @Test
    public void convertToBytes() {
        assertTrue(Arrays.equals(new byte[] { 100, 97, 116, 97 }, StringToBytes
                .withDefaultCharset().convert("data")));
    }
}
