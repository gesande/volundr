package org.fluentjava.volundr.concurrent;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

final class ThreadEngine {
    private final static Logger LOGGER = LoggerFactory
            .getLogger(ThreadEngine.class);
    private final ThreadFactory threadFactory;
    private final List<Thread> threads = new ArrayList<>();

    public ThreadEngine(final ThreadFactory threadFactory) {
        this.threadFactory = threadFactory;
    }

    /**
     * Running the given runnables, this call is blocking until all threads are
     * done.
     */
    @SafeVarargs
    public final <RUNNABLE extends Runnable> void run(
            final RUNNABLE... runnables) {
        requireNonNull(runnables);
        if (runnables.length == 0) {
            LOGGER.info(
                    "There was nothing to do, no runnables were given. Exiting.");
            return;
        }
        initializeWith(runnables);
        startThreads();
        joinThreads();
        clearThreads();
    }

    private void clearThreads() {
        this.threads.clear();
        LOGGER.debug("Threads 'cleared'.");
    }

    @SafeVarargs
    private <T extends Runnable> void initializeWith(
            final T... runnables) {
        Arrays.asList(runnables)
                .forEach(t -> threads.add(threadFactory().newThread(t)));
    }

    private ThreadFactory threadFactory() {
        return this.threadFactory;
    }

    private void startThreads() {
        LOGGER.debug("Starting  threads...");
        threads.forEach(Thread::start);
        LOGGER.debug("Threads started.");
    }

    private void joinThreads() {
        threads.forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException ignore) {
                LOGGER.warn("Thread join interrupted");
            }
        });
    }

    public void interruptThreads() {
        LOGGER.debug("Interrupting threads...");
        threads.forEach(Thread::interrupt);
        LOGGER.debug("Threads interrupted.");
    }

}
