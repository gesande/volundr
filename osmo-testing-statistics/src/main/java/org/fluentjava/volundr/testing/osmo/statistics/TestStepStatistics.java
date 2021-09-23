package org.fluentjava.volundr.testing.osmo.statistics;

import java.time.Clock;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

public class TestStepStatistics {
    private final List<Long> latencies = Collections
            .synchronizedList(new ArrayList<>());
    private long start;
    private final Clock clock;
    private final TestStepStatisticsPublisher testStepStatisticsPublisher;

    public TestStepStatistics(String name, Clock clock,
            StatisticsApiBuilder statisticsApiBuilder) {
        this.clock = clock;
        this.testStepStatisticsPublisher = new TestStepStatisticsPublisher(name,
                statisticsApiBuilder);
    }

    public void start() {
        start = clock.millis();
    }

    public void stop() {
        synchronized (latencies) {
            long end = clock.millis();
            long latency = end - start;
            latencies.add(latency);
        }
    }

    public void consumeStatistics(Consumer<String> consumer) {
        testStepStatisticsPublisher.publish(latencies, consumer);
    }
}

class TestStepStatisticsPublisher {

    private final StatisticsApiBuilder statisticsApiBuilder;
    private final String name;

    public TestStepStatisticsPublisher(String name,
            StatisticsApiBuilder statisticsApiBuilder) {
        this.name = name;
        this.statisticsApiBuilder = statisticsApiBuilder;
    }

    public void publish(List<Long> latencies, Consumer<String> consumer) {
        statisticsApiBuilder.withValues(latencies)
                .statsTitle("Test step '" + name
                        + "' latency statistics (in milliseconds):")
                .consumedBy(consumer)
                .frequencyGraph("Test step '" + name + "' latency statistics",
                        "Latencies (ms)",
                        "test-step-" + name + "-latency-frequency-statistics")
                .publish();
    }

}
