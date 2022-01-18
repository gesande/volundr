package org.fluentjava.volundr.testing.osmo;

import static org.fluentjava.volundr.testing.osmo.statistics.SleepValueProvider.calculateNextSleepValue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.time.Clock;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

import org.fluentjava.volundr.testing.osmo.model.logging.LoggingModel;
import org.fluentjava.volundr.testing.osmo.model.statistics.StatisticsModel;
import org.fluentjava.volundr.testing.osmo.statistics.SleepValueProvider;
import org.fluentjava.volundr.testing.osmo.statistics.StatisticsConsumer;
import org.fluentjava.volundr.testing.osmo.statistics.StatisticsMeasurement;
import org.junit.jupiter.api.Test;

import lombok.extern.slf4j.Slf4j;
import osmo.tester.generator.endcondition.Length;

@Slf4j
public class OsmoRunnerExampleTest {

    @Test
    public void testOsmoRunnerWithAllItsBellsAndWhistles() throws IOException {
        Length endCondition = new Length(30); // 30 steps
        long seed = System.currentTimeMillis();
        Random random = new Random(seed);

        String targetPath = Files
                .createTempDirectory(UUID.randomUUID().toString()).toFile()
                .getAbsolutePath();
        String jenkinsReportName = targetPath + "/jenkins-report.xml";
        Clock clock = Clock.systemDefaultZone();
        StatisticsConsumer statsConsumer = new StatisticsConsumer();
        SleepValueProvider sleepValueProvider = () -> calculateNextSleepValue(
                200L, random);
        AtomicInteger calls = new AtomicInteger(0);
        OSMOTesterBuilder.builder().weightedBalancingAlgorithm().oneSuite()

                .models(new LoggingModel(),

                        new StatisticsModel(StatisticsMeasurement
                                .create(statsConsumer, clock, targetPath),
                                sleepValueProvider))
                .models(new HelloWorldModel(calls))

                .endCondition(endCondition)

                .withJenkinsReport(jenkinsReportName)

                .build(seed).run();
        log.info(statsConsumer.stats());
        assertEquals(30, calls.get());
    }
}
