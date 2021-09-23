package org.fluentjava.volundr.concurrent;

import static org.junit.Assert.assertTrue;

import java.util.concurrent.atomic.AtomicBoolean;

import org.junit.Test;

public class ThreadEngineTest {

    @Test(expected = NullPointerException.class)
    public void nullRunnables() {
        newEngineWithNamedThreadFactory().run((Runnable[]) null);
    }

    @Test
    public void empty() {
        newEngineWithNamedThreadFactory().run();
    }

    @Test
    public void runRunnables() {
        final AtomicBoolean runned = new AtomicBoolean(false);
        final Runnable r = () -> runned.set(true);
        newEngineWithNamedThreadFactory().run(r);
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
        final ThreadEngine newEngineWithNamedThreadFactory = newEngineWithNamedThreadFactory();
        new Thread(() -> newEngineWithNamedThreadFactory.run(r)).start();
        Thread.sleep(100);
        new Thread(newEngineWithNamedThreadFactory::interruptThreads).start();
        Thread.sleep(100);
        assertTrue(wasInterrupted.get());
    }

    private static ThreadEngine newEngineWithNamedThreadFactory() {
        return new ThreadEngine(
                NamedThreadFactory.forNamePrefix("my-test-threads-"));
    }

}
