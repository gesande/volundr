package org.fluentjava.volundr.testing.osmo.model.logging;

import org.fluentjava.volundr.testing.osmo.AbstractOsmoModel;
import org.fluentjava.volundr.testing.osmo.model.statistics.StatisticsModel;

import lombok.extern.slf4j.Slf4j;
import osmo.tester.annotation.AfterStep;
import osmo.tester.annotation.AfterSuite;
import osmo.tester.annotation.BeforeStep;

/**
 * This provides basic logging when test step starts or its done. Usually this
 * is added a first model. However if you are using also {@link StatisticsModel}
 * add this right after that.
 */
@Slf4j
public class LoggingModel extends AbstractOsmoModel {

    @BeforeStep
    public void logSteps() {
        // Log test and step number before each step
        log.info("{} start", msg());
    }

    @AfterStep
    public void afterStep() {
        // Log test and step number after each step
        log.info("{} done", msg());
    }

    @AfterSuite
    public void afterSuite() {
        log.info(totalTestsGeneratedMessage());
    }

    private String msg() {
        return "[" + currentTestStepName() + ":"
                + (suite.currentTestNumber() + 1) + ":" + suite.currentSteps()
                + "]";
    }
}
