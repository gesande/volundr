package org.fluentjava.völundr.testing.osmo.logging;

import org.fluentjava.völundr.testing.osmo.AbstractOsmoModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import osmo.tester.annotation.AfterStep;
import osmo.tester.annotation.AfterSuite;
import osmo.tester.annotation.BeforeStep;

/**
 * This provides basic logging when test step starts or its done. Usually this is added a first model.
 * However if you are using also {@link org.fluentjava.völundr.testing.osmo.statistics.StatisticsModel} add this
 * right after that.
 */
public class LoggingModel extends AbstractOsmoModel {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoggingModel.class);

    @BeforeStep
    public void logSteps() {
        // Log test and step number before each step
        LOGGER.info("{} start", msg());
    }

    @AfterStep
    public void afterStep() {
        // Log test and step number after each step
        LOGGER.info("{} done", msg());
    }

    @AfterSuite
    public void afterSuite() {
        LOGGER.info(totalTestsGeneratedMessage());
    }

    private String msg() {
        return "[" + currentTestStepName() + ":" + (suite.currentTestNumber() + 1) + ":" + suite.currentSteps() + "]";
    }
}
