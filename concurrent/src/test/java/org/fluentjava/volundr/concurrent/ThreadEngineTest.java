package org.fluentjava.volundr.concurrent;

import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import org.junit.jupiter.api.Test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ThreadEngineTest {

    @Test
    public void nullRunnables() {
        assertThrows(NullPointerException.class,
                () -> newEngineWithNamedThreadFactory().run((Runnable[]) null));
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
    public void interruptThreads() {
        final AtomicBoolean wasInterrupted = new AtomicBoolean(false);
        final AtomicBoolean running = new AtomicBoolean(false);
        final Runnable r = () -> {
            try {
                log.debug("Running...");
                running.set(true);
                // sleepy thread
                log.debug("Sleep");
                Thread.sleep(1000);
                log.debug("Done");
            } catch (InterruptedException e) {
                log.debug("Interrupted");
                wasInterrupted.set(true);
            }
        };
        final ThreadEngine newEngineWithNamedThreadFactory = newEngineWithNamedThreadFactory();

        new Thread(() -> newEngineWithNamedThreadFactory.run(r)).start();
        await().pollDelay(25, TimeUnit.MILLISECONDS)
                .atMost(200, TimeUnit.MILLISECONDS).until(running::get);
        newEngineWithNamedThreadFactory.interruptThreads();
        await().pollDelay(25, TimeUnit.MILLISECONDS)
                .atMost(100, TimeUnit.MILLISECONDS).until(wasInterrupted::get);
    }

    private static ThreadEngine newEngineWithNamedThreadFactory() {
        return new ThreadEngine(
                NamedThreadFactory.forNamePrefix("my-test-threads-"));
    }

}
