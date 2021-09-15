package org.fluentjava.volundr.testing.osmo.model.statistics;

import org.fluentjava.volundr.testing.osmo.AbstractOsmoModel;
import org.fluentjava.volundr.testing.osmo.statistics.SleepValueProvider;
import org.fluentjava.volundr.testing.osmo.statistics.StatisticsMeasurement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import osmo.tester.annotation.AfterStep;
import osmo.tester.annotation.AfterTest;
import osmo.tester.annotation.BeforeStep;

/**
 * This model is used for measuring step latencies. Usually you should add this
 * model before any other model.
 */
public class StatisticsModel extends AbstractOsmoModel {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(StatisticsModel.class);
    private final StatisticsMeasurement statisticsMeasurement;
    private final SleepValueProvider sleepValueProvider;

    public StatisticsModel(StatisticsMeasurement statisticsMeasurement,
            SleepValueProvider sleepValueProvider) {
        super();
        this.statisticsMeasurement = statisticsMeasurement;
        this.sleepValueProvider = sleepValueProvider;
    }

    @BeforeStep
    public void startStatisticsMeasurement() {
        statisticsMeasurement.start(currentTestStepName());
    }

    @AfterStep
    public void stopStatisticsMeasurement() {
        statisticsMeasurement.stop(currentTestStepName());
        sleepAfterEachStep();
    }

    @AfterTest
    public void consumeStatistics() {
        statisticsMeasurement.consumeStatistics();
    }

    /**
     * This is here to avoid sleep values to be included to the measurements.
     */
    private void sleepAfterEachStep() {
        long sleepTime = sleepValueProvider.get();
        LOGGER.debug("{} Sleeping {} ms", currentTestStepName(), sleepTime);
        try {
            Thread.sleep(sleepTime);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
