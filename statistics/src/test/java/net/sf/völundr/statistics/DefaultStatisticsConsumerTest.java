package net.sf.völundr.statistics;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class DefaultStatisticsConsumerTest {

    @SuppressWarnings("static-method")
    @Test
    public void emptyTest() {
        final List<Long> latencies = new ArrayList<>();
        DefaultStatisticsConsumer consumer = DefaultStatisticsConsumer
                .statsConsumer("title", latencies);

        consumer.consumeStatistics(stats -> {
            assertEquals("title\n" + "samples       : 0\n"
                    + "max           : 0\n" + "min           : 0\n"
                    + "mean          : 0.0\n" + "median        : 0\n"
                    + "95 percentile : 0\n" + "std           : NaN\n"
                    + "variance      : NaN\n" + "", stats);
        });
    }

    @SuppressWarnings("static-method")
    @Test
    public void valuesTest() {
        final List<Long> latencies = new ArrayList<>();
        for (long i = 0; i < 100; i++) {
            latencies.add(i);
        }
        DefaultStatisticsConsumer consumer = DefaultStatisticsConsumer
                .statsConsumer("title", latencies);

        consumer.consumeStatistics(stats -> {
            assertEquals(
                    "title\n" + "samples       : 100\n" + "max           : 99\n"
                            + "min           : 0\n" + "mean          : 49.5\n"
                            + "median        : 49\n" + "95 percentile : 95\n"
                            + "std           : 28.86607004772212\n"
                            + "variance      : 833.25\n" + "",
                    stats);
        });
    }

}
