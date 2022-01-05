package org.fluentjava.volundr.concurrent;

import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static org.awaitility.Awaitility.await;
import static org.junit.Assert.assertEquals;

public class ExecutorServiceFactoryTest {

    @Test
    public void once() {
        final ExecutorService newFixedThreadPool = new ExecutorServiceFactory()
                .newFixedThreadPool(1, "my-thread");
        final AtomicInteger i = new AtomicInteger(0);
        newFixedThreadPool.execute(i::getAndIncrement);
        await().atLeast(100, TimeUnit.MILLISECONDS).until(() -> 1 == i.get());
        assertEquals(1, i.get());
    }
}
