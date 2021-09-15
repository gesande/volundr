package org.fluentjava.volundr.statistics;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class PercentileRankCalculatorTest {

    @Test
    public void nearestRank() {
        final int samples = 2;
        assertEquals(0.5, nearestRank(0, samples), 0);
        assertEquals(0.6, nearestRank(5, samples), 0);
        assertEquals(0.7, nearestRank(10, samples), 0);
        assertEquals(0.8, nearestRank(15, samples), 0);
        assertEquals(0.9, nearestRank(20, samples), 0);
        assertEquals(1.0, nearestRank(25, samples), 0);
        assertEquals(1.1, nearestRank(30, samples), 0);
        assertEquals(1.2, nearestRank(35, samples), 0);
        assertEquals(1.3, nearestRank(40, samples), 0);
        assertEquals(1.4, nearestRank(45, samples), 0);
        assertEquals(1.5, nearestRank(50, samples), 0);
        assertEquals(1.6, nearestRank(55, samples), 0);
        assertEquals(1.7, nearestRank(60, samples), 0);
        assertEquals(1.8, nearestRank(65, samples), 0);
        assertEquals(1.9, nearestRank(70, samples), 0);
        assertEquals(2.0, nearestRank(75, samples), 0);
        assertEquals(2.1, nearestRank(80, samples), 0);
        assertEquals(2.2, nearestRank(85, samples), 0);
        assertEquals(2.3, nearestRank(90, samples), 0);
        assertEquals(2.4, nearestRank(95, samples), 0);
        assertEquals(2.5, nearestRank(100, samples), 0);
    }

    @Test
    public void nearestRank100() {
        final int samples = 100;
        assertEquals(0.5, nearestRank(0, samples), 0);
        assertEquals(5.5, nearestRank(5, samples), 0);
        assertEquals(10.5, nearestRank(10, samples), 0);
        assertEquals(15.5, nearestRank(15, samples), 0);
        assertEquals(20.5, nearestRank(20, samples), 0);
        assertEquals(25.5, nearestRank(25, samples), 0);
        assertEquals(30.5, nearestRank(30, samples), 0);
        assertEquals(35.5, nearestRank(35, samples), 0);
        assertEquals(40.5, nearestRank(40, samples), 0);
        assertEquals(45.5, nearestRank(45, samples), 0);
        assertEquals(50.5, nearestRank(50, samples), 0);
        assertEquals(55.5, nearestRank(55, samples), 0);
        assertEquals(60.5, nearestRank(60, samples), 0);
        assertEquals(65.5, nearestRank(65, samples), 0);
        assertEquals(70.5, nearestRank(70, samples), 0);
        assertEquals(75.5, nearestRank(75, samples), 0);
        assertEquals(80.5, nearestRank(80, samples), 0);
        assertEquals(85.5, nearestRank(85, samples), 0);
        assertEquals(90.5, nearestRank(90, samples), 0);
        assertEquals(95.5, nearestRank(95, samples), 0);
        assertEquals(100.5, nearestRank(100, samples), 0);
    }

    private static double nearestRank(final int percentile, final int samples) {
        return PercentileRankCalculator.nearestRank(percentile, samples);
    }
}
