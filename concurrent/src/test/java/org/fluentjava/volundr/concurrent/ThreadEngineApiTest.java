package org.fluentjava.volundr.concurrent;

import static org.junit.Assert.assertTrue;

import java.util.concurrent.atomic.AtomicBoolean;

import org.junit.Test;

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
    public void interruptThreads() throws InterruptedException {
        final AtomicBoolean wasInterrupted = new AtomicBoolean(false);
        final Runnable r = () -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                wasInterrupted.set(true);
            }
        };
        ThreadEngineApi<Runnable> threadEngineApi = ThreadEngineApi.builder()
                .threadNamePrefix("my-test-threads-").runnables(r).build();
        new Thread(threadEngineApi::run).start();
        Thread.sleep(100);
        new Thread(threadEngineApi::interrupt).start();
        Thread.sleep(100);
        assertTrue(wasInterrupted.get());
    }
}
