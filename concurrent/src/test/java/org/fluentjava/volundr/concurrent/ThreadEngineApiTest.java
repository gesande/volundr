package org.fluentjava.volundr.concurrent;

import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import org.junit.jupiter.api.Test;

public class ThreadEngineApiTest {

    @Test
    public void nullRunnables() {
        ThreadEngineApi.builder().threadNamePrefix("my-test-threads-")
                .runnables((Runnable) null).build().run();
    }

    @Test
    public void empty() {
        ThreadEngineApi.builder().threadNamePrefix("my-test-threads-")
                .runnables().build().run();
    }

    @Test
    public void runRunnables() {
        final AtomicBoolean runned = new AtomicBoolean(false);
        final Runnable r = () -> runned.set(true);
        ThreadEngineApi.builder().threadNamePrefix("my-test-threads-")
                .runnables(r).build().run();
        assertTrue(runned.get());
    }

    @Test
    public void interruptThreads() {
        final AtomicBoolean wasInterrupted = new AtomicBoolean(false);
        final AtomicBoolean running = new AtomicBoolean(false);
        final Runnable r = () -> {
            try {
                running.set(true);
                // sleepy thread
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                wasInterrupted.set(true);
            }
        };
        ThreadEngineApi<Runnable> threadEngineApi = ThreadEngineApi.builder()
                .threadNamePrefix("my-test-threads-").runnables(r).build();
        new Thread(threadEngineApi::run).start();
        await().pollDelay(25, TimeUnit.MILLISECONDS)
                .atMost(100, TimeUnit.MILLISECONDS).until(running::get);
        new Thread(threadEngineApi::interrupt).start();
        await().pollDelay(25, TimeUnit.MILLISECONDS)
                .atMost(100, TimeUnit.MILLISECONDS).until(wasInterrupted::get);
    }
}
