package org.fluentjava.volundr.statistics;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class StatisticsValueProviderTest {

    @Test
    public void empty() {
        final StatisticsValueProvider stat = new StatisticsValueProvider();
        assertEquals(0, stat.min(), 0, "Min doesn't match!");
        assertEquals(0, stat.max(), 0, "Max doesn't match!");
        assertEquals(0, stat.mean(), 0, "Mean doesn't match!");
        assertEquals(0, stat.median(), 0, "Median doesn't match!");
        stat.percentile(0);
        for (int i = 1; i < 101; i++) {
            assertEquals(0, stat.percentile(i), 0,
                    i + " percentile doesn't match!");
        }
    }

    @Test
    public void statsOdd() {
        final StatisticsValueProvider stat = new StatisticsValueProvider();
        for (int i = 100; i > -1; i--) {
            stat.addSample(i);
        }
        assertEquals(0, stat.min(), 0, "Min doesn't match!");
        assertEquals(100, stat.max(), 0, "Max doesn't match!");
        assertEquals(50, stat.mean(), 0, "Mean doesn't match!");
        assertEquals(50, stat.median(), 0, "Median doesn't match!");
        assertEquals(50, stat.percentile(50), 0,
                "50 percentile doesn't match!");
        assertEquals(90, stat.percentile(90), 0,
                "90 percentile doesn't match!");
        assertEquals(95, stat.percentile(95), 0,
                "95 percentile doesn't match!");
        assertEquals(96, stat.percentile(96), 0,
                "96 percentile doesn't match!");
        assertEquals(97, stat.percentile(97), 0,
                "97 percentile doesn't match!");
        assertEquals(98, stat.percentile(98), 0,
                "98 percentile doesn't match!");
        assertEquals(99, stat.percentile(99), 0,
                "99 percentile doesn't match!");
        assertEquals(100, stat.percentile(100), 0,
                "100 percentile doesn't match!");
    }

    @Test
    public void statsEven() {
        final StatisticsValueProvider stat = new StatisticsValueProvider();
        for (int i = 100; i > 0; i--) {
            stat.addSample(i);
        }
        assertEquals(1, stat.min(), 0, "Min doesn't match!");
        assertEquals(100, stat.max(), 0, "Max doesn't match!");
        assertEquals(50.5, stat.mean(), 0, "Mean doesn't match!");
        assertEquals(50, stat.median(), 0, "Median doesn't match!");
        assertEquals(51, stat.percentile(50), 0,
                "50 percentile doesn't match!");
        assertEquals(91, stat.percentile(90), 0,
                "90 percentile doesn't match!");
        assertEquals(96, stat.percentile(95), 0,
                "95 percentile doesn't match!");
        assertEquals(97, stat.percentile(96), 0,
                "96 percentile doesn't match!");
        assertEquals(98, stat.percentile(97), 0,
                "97 percentile doesn't match!");
        assertEquals(99, stat.percentile(98), 0,
                "98 percentile doesn't match!");
        assertEquals(100, stat.percentile(99), 0,
                "99 percentile doesn't match!");
        assertEquals(100, stat.percentile(100), 0,
                "100 percentile doesn't match!");
    }

    @Test
    public void percentileTest() {
        final StatisticsValueProvider stat = new StatisticsValueProvider();
        stat.addSample(15);
        stat.addSample(20);
        stat.addSample(35);
        stat.addSample(40);
        stat.addSample(50);
        assertEquals(20, stat.percentile(30), 0);
        assertEquals(20, stat.percentile(35), 0);
        assertEquals(35, stat.percentile(40), 0);
        assertEquals(50, stat.percentile(100), 0);
    }

    @Test
    public void performance() {
        final StatisticsValueProvider stat = new StatisticsValueProvider();
        for (int i = 1000000; i > -1; i--) {
            stat.addSample(i);
        }
        assertEquals(0, stat.min(), 0, "Min doesn't match!");
        assertEquals(1000000, stat.max(), 0, "Max doesn't match!");
        assertEquals(500000, stat.mean(), 0, "Mean doesn't match!");
        assertEquals(500000, stat.median(), 0, "Median doesn't match!");
        assertEquals(500000, stat.percentile(50), 0,
                "50 percentile doesn't match!");
        assertEquals(900000, stat.percentile(90), 0,
                "90 percentile doesn't match!");
        assertEquals(950000, stat.percentile(95), 0,
                "95 percentile doesn't match!");
        assertEquals(960000, stat.percentile(96), 0,
                "96 percentile doesn't match!");
        assertEquals(970000, stat.percentile(97), 0,
                "97 percentile doesn't match!");
        assertEquals(980000, stat.percentile(98), 0,
                "98 percentile doesn't match!");
        assertEquals(990000, stat.percentile(99), 0,
                "99 percentile doesn't match!");
    }

}
