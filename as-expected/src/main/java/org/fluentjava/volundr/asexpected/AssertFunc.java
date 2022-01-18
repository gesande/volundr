package org.fluentjava.volundr.asexpected;

@FunctionalInterface
public interface AssertFunc {
    void assertEquals(String expected, String actual);
}
