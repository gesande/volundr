package org.fluentjava.volundr.concurrent;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertTrue;

@Slf4j
public class ThreadEngineApiExample {

    @Test
    public void example() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        Runnable runnable = () -> {
            latch.countDown();
            log.info("My runnable got run. Nice :)");
        };
        ThreadEngineApi.builder().threadNamePrefix("threadname-prefix-")
                .runnables(runnable).build().run();
        assertTrue(latch.await(1, TimeUnit.SECONDS));
    }

}
