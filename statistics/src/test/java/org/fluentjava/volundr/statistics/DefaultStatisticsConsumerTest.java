package org.fluentjava.volundr.statistics;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

@SuppressWarnings("PMD.AddEmptyString")
public class DefaultStatisticsConsumerTest {

    @Test
    public void emptyTest() {
        final List<Long> latencies = new ArrayList<>();
        DefaultStatisticsConsumer consumer = DefaultStatisticsConsumer
                .statsConsumer("title", latencies);

        consumer.consumeStatistics(
                stats -> assertEquals("""
                        title
                        samples       : 0
                        max           : 0
                        min           : 0
                        mean          : 0.0
                        median        : 0
                        95 percentile : 0
                        std           : NaN
                        variance      : NaN
                        """, stats));
    }

    @Test
    public void valuesTest() {
        final List<Long> latencies = new ArrayList<>();
        for (long i = 0; i < 100; i++) {
            latencies.add(i);
        }
        DefaultStatisticsConsumer consumer = DefaultStatisticsConsumer
                .statsConsumer("title", latencies);

        consumer.consumeStatistics(stats -> assertEquals(
                """
                        title
                        samples       : 100
                        max           : 99
                        min           : 0
                        mean          : 49.5
                        median        : 49
                        95 percentile : 95
                        std           : 28.86607004772212
                        variance      : 833.25
                        """,
                stats));
    }

}
