package org.fluentjava.volundr.statistics;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class StatisticsValueProviderFactoryTest {

    @Test
    public void emptyWithIntegerValues() {
        AbstractStatisticsValueProvider<Integer> stat = StatisticsValueProviderFactory
                .integerValues();
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
    public void emptyWithLongValues() {
        AbstractStatisticsValueProvider<Long> stat = StatisticsValueProviderFactory
                .longValues();
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
    public void statsOddLongValues() {
        AbstractStatisticsValueProvider<Long> stat = StatisticsValueProviderFactory
                .longValues();
        for (long i = 100; i > -1; i--) {
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
    public void statsEvenLongValues() {
        AbstractStatisticsValueProvider<Long> stat = StatisticsValueProviderFactory
                .longValues();
        for (long i = 100; i > 0; i--) {
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

}
