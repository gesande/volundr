package org.fluentjava.volundr.concurrent;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class NamedThreadFactoryTest {

    @Test
    public void newThread() {
        final Runnable r = () -> {
            //
        };
        final Thread newThread = NamedThreadFactory.forNamePrefix("prefix")
                .newThread(r);
        assertNotNull(newThread);
        final String name = newThread.getName();
        assertTrue(name.contains("prefix"));
        assertTrue(name.startsWith("prefix"));
    }
}
