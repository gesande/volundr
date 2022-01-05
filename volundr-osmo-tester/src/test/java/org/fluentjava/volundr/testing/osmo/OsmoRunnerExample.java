package org.fluentjava.volundr.testing.osmo;

import lombok.extern.slf4j.Slf4j;
import org.fluentjava.volundr.testing.osmo.model.logging.LoggingModel;
import org.fluentjava.volundr.testing.osmo.model.statistics.StatisticsModel;
import org.fluentjava.volundr.testing.osmo.statistics.SleepValueProvider;
import org.fluentjava.volundr.testing.osmo.statistics.StatisticsConsumer;
import org.fluentjava.volundr.testing.osmo.statistics.StatisticsMeasurement;
import org.junit.Test;
import osmo.tester.generator.endcondition.Length;

import java.time.Clock;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import static org.fluentjava.volundr.testing.osmo.statistics.SleepValueProvider.calculateNextSleepValue;
import static org.junit.Assert.assertEquals;

@Slf4j
public class OsmoRunnerExample {

    @Test
    public void testOsmoRunnerWithAllItsBellsAndWhistles() {
        Length endCondition = new Length(30); //30 steps
        long seed = System.currentTimeMillis();
        Random random = new Random(seed);
        String targetPath = System.getProperty("user.dir") + "/target";
        String jenkinsReportName = "jenkins-report.xml";
        Clock clock = Clock.systemDefaultZone();
        StatisticsConsumer statsConsumer = new StatisticsConsumer();
        SleepValueProvider sleepValueProvider = () -> calculateNextSleepValue(200L, random);
        AtomicInteger calls = new AtomicInteger(0);
        OSMOTesterBuilder.builder().weightedBalancingAlgorithm().oneSuite()

                .models(new LoggingModel(),

                        new StatisticsModel(StatisticsMeasurement.create(statsConsumer, clock, targetPath),
                                sleepValueProvider))
                .models(new HelloWorldModel(calls))

                .endCondition(endCondition)

                .withJenkinsReport(jenkinsReportName)

                .build(seed).run();
        log.info(statsConsumer.stats());
        assertEquals(30, calls.get());
    }
}
