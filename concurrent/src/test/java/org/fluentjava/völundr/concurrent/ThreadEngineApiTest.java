package org.fluentjava.völundr.concurrent;

import static org.junit.Assert.assertTrue;

import java.util.concurrent.atomic.AtomicBoolean;

import org.junit.Test;

public class ThreadEngineApiTest {

    @Test(expected = IllegalArgumentException.class)
    public void nullRunnables() {
        new ThreadEngineApi<>().threadNamePrefix("my-test-threads-").run();
    }

    @Test
    public void empty() {
        new ThreadEngineApi<>().threadNamePrefix("my-test-threads-")
                .runnables(new Runnable[0]).run();
    }

    @Test
    public void runRunnables() {
        final AtomicBoolean runned = new AtomicBoolean(false);
        final Runnable r = new Runnable() {
            @Override
            public void run() {
                runned.set(true);
            }
        };
        new ThreadEngineApi<>().threadNamePrefix("my-test-threads-")
                .runnables(new Runnable[] { r }).run();
        assertTrue(runned.get());
    }

    @Test
    public void interruptThreads() throws InterruptedException {
        final AtomicBoolean wasInterrupted = new AtomicBoolean(false);
        final Runnable r = new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    wasInterrupted.set(true);
                }
            }
        };
        final ThreadEngineApi<Runnable> threadEngineApi = new ThreadEngineApi<>();
        new Thread(new Runnable() {

            @Override
            public void run() {
                threadEngineApi.threadNamePrefix("my-test-threads-")
                        .runnables(new Runnable[] { r }).run();
            }
        }).start();
        Thread.sleep(100);
        new Thread(new Runnable() {

            @Override
            public void run() {
                threadEngineApi.interrupt();
            }
        }).start();
        Thread.sleep(100);
        assertTrue(wasInterrupted.get());
    }
}
