package org.fluentjava.volundr.asexpected;

public interface Expected<PARENT> {

    Expected<PARENT> string(final String string);

    Expected<PARENT> line(final String line);

    /**
     * Ends the expectation, i.e. does the assert.
     */
    PARENT end();

}
