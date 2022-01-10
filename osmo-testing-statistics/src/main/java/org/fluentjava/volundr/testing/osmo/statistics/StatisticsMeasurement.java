package org.fluentjava.volundr.testing.osmo.statistics;

import static java.util.Objects.requireNonNull;

import java.time.Clock;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StatisticsMeasurement {

    private final static Logger LOGGER = LoggerFactory
            .getLogger(StatisticsMeasurement.class);

    private final Map<String, TestStepStatistics> stats = new ConcurrentHashMap<>();
    private final Consumer<String> statsConsumer;
    private final Clock clock;
    private final StatisticsApiBuilder statisticsApiBuilder;

    public StatisticsMeasurement(Consumer<String> statsConsumer, Clock clock,
            StatisticsApiBuilder statisticsApiBuilder) {
        this.statsConsumer = requireNonNull(statsConsumer);
        this.clock = requireNonNull(clock);
        this.statisticsApiBuilder = requireNonNull(statisticsApiBuilder);
    }

    public void start(String currentStep) {
        requireNonNull(currentStep);
        TestStepStatistics testStepStatistics = createOrUseExisting(
                currentStep);
        testStepStatistics.start();
    }

    private TestStepStatistics createOrUseExisting(String currentStep) {
        TestStepStatistics testStepStatistics;
        if (stats.containsKey(currentStep)) {
            testStepStatistics = stats.get(currentStep);
        } else {
            LOGGER.debug("{} creating TestStepStatistics...", currentStep);
            testStepStatistics = new TestStepStatistics(currentStep, clock,
                    statisticsApiBuilder);
            stats.put(currentStep, testStepStatistics);
        }
        return testStepStatistics;
    }

    public void stop(String currentStep) {
        requireNonNull(currentStep);
        stats.get(currentStep).stop();
    }

    public void consumeStatistics() {
        Collection<TestStepStatistics> values = stats.values();
        values.forEach(stepStats -> stepStats.consumeStatistics(statsConsumer));
    }

    public static StatisticsMeasurement create(StatisticsConsumer statsConsumer,
            Clock clock, String targetPath) {
        return new StatisticsMeasurement(statsConsumer, clock,
                StatisticsApiBuilderImpl.withTargetPath(targetPath));
    }

}
