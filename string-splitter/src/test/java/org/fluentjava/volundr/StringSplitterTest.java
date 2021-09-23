package org.fluentjava.volundr;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class StringSplitterTest {

    @Test
    public void justSeparator() {
        final String[] result = new StringSplitter(";").split(";");
        assertEquals(0, result.length);
    }

    @Test
    public void noSeparator() {
        final String[] result = new StringSplitter(";").split("");
        assertEquals(1, result.length);
        assertEquals("", result[0]);
    }

    @Test
    public void fields() {
        final String[] result = new StringSplitter(";").split("1;2");
        assertEquals(2, result.length);
        assertEquals("1", result[0]);
        assertEquals("2", result[1]);
    }

}
