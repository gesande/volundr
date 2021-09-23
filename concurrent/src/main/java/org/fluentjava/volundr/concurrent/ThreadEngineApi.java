package org.fluentjava.volundr.concurrent;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Easy API for running something being or extending Runnable in threads.
 */
public final class ThreadEngineApi<RUNNABLE extends Runnable> {

    private final List<RUNNABLE> runnables = new ArrayList<>();
    private final ThreadEngine threadEngine;

    private ThreadEngineApi(ThreadEngine engine, List<RUNNABLE> runnables) {
        this.threadEngine = engine;
        this.runnables.addAll(runnables);
    }

    /**
     * This call blocks until all threads are finished.
     */
    public void run() {
        engine().run(runnables.toArray(new Runnable[0]));
    }

    /**
     * Interrupts threads running the runnables.
     */
    public void interrupt() {
        engine().interruptThreads();
    }

    private ThreadEngine engine() {
        return this.threadEngine;
    }

    public static class Builder<RUNNABLE extends Runnable> {
        private ThreadEngine threadEngine;
        private final List<RUNNABLE> runnables = new ArrayList<>();

        /**
         * When a thread is created this prefix is used as a part for its name.
         *
         * @param threadNamePrefix
         *                             thread name prefix
         * @return Builder
         */
        public Builder<RUNNABLE> threadNamePrefix(String threadNamePrefix) {
            threadEngine = new ThreadEngine(NamedThreadFactory
                    .forNamePrefix(requireNonNull(threadNamePrefix)));
            return this;
        }

        @SafeVarargs
        public final Builder<RUNNABLE> runnables(final RUNNABLE... runnables) {
            this.runnables.addAll(Arrays.asList(requireNonNull(runnables)));
            return this;
        }

        public ThreadEngineApi<RUNNABLE> build() {
            return new ThreadEngineApi<>(requireNonNull(threadEngine),
                    requireNonNull(runnables));
        }

    }

    public static <T extends Runnable> Builder<T> builder() {
        return new Builder<>();
    }
}
