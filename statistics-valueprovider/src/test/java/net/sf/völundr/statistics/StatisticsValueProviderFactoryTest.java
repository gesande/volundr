package net.sf.völundr.statistics;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class StatisticsValueProviderFactoryTest {

    @SuppressWarnings("static-method")
    @Test
    public void emptyWithIntegerValues() {
        AbstractStatisticsValueProvider<Integer> stat = StatisticsValueProviderFactory
                .integerValues();
        assertEquals("Min doesn't match!", 0, stat.min(), 0);
        assertEquals("Max doesn't match!", 0, stat.max(), 0);
        assertEquals("Mean doesn't match!", 0, stat.mean(), 0);
        assertEquals("Median doesn't match!", 0, stat.median(), 0);
        stat.percentile(0);
        for (int i = 1; i < 101; i++) {
            assertEquals(i + " percentile doesn't match!", 0,
                    stat.percentile(i), 0);
        }
    }

    @SuppressWarnings("static-method")
    @Test
    public void emptyWithLongValues() {
        AbstractStatisticsValueProvider<Long> stat = StatisticsValueProviderFactory
                .longValues();
        assertEquals("Min doesn't match!", 0, stat.min(), 0);
        assertEquals("Max doesn't match!", 0, stat.max(), 0);
        assertEquals("Mean doesn't match!", 0, stat.mean(), 0);
        assertEquals("Median doesn't match!", 0, stat.median(), 0);
        stat.percentile(0);
        for (int i = 1; i < 101; i++) {
            assertEquals(i + " percentile doesn't match!", 0,
                    stat.percentile(i), 0);
        }
    }
}
