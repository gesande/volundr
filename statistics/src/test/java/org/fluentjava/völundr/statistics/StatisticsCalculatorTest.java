package org.fluentjava.völundr.statistics;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class StatisticsCalculatorTest {

    @Test
    public void statsOdd() {
        final List<Integer> values = new ArrayList<>();
        for (int i = 100; i > -1; i--) {
            values.add(i);
        }
        final StatisticsCalculator stat = StatisticsCalculator
                .fromValues(values);
        assertEquals("Min doesn't match!", 0, stat.min(), 0);
        assertEquals("Max doesn't match!", 100, stat.max(), 0);
        assertEquals("Mean doesn't match!", 50, stat.mean(), 0);
        assertEquals("Median doesn't match!", 50, stat.median(), 0);
        assertEquals("50 percentile doesn't match!", 50, stat.percentile(50),
                0);
        assertEquals("90 percentile doesn't match!", 90, stat.percentile(90),
                0);
        assertEquals("95 percentile doesn't match!", 95, stat.percentile(95),
                0);
        assertEquals("96 percentile doesn't match!", 96, stat.percentile(96),
                0);
        assertEquals("97 percentile doesn't match!", 97, stat.percentile(97),
                0);
        assertEquals("98 percentile doesn't match!", 98, stat.percentile(98),
                0);
        assertEquals("99 percentile doesn't match!", 99, stat.percentile(99),
                0);
        assertEquals("Standard deviation doesn't match!", 29.154759474226502,
                stat.standardDeviation(), 0);
        assertEquals("Standard deviation doesn't match!", 850.0,
                stat.variance(), 0);
    }

    @Test
    public void statsEven() {
        final List<Integer> values = new ArrayList<>();
        for (int i = 100; i > 0; i--) {
            values.add(i);
        }
        final StatisticsCalculator stat = StatisticsCalculator
                .fromValues(values);
        assertEquals("Min doesn't match!", 1, stat.min(), 0);
        assertEquals("Max doesn't match!", 100, stat.max(), 0);
        assertEquals("Mean doesn't match!", 50.5, stat.mean(), 0);
        assertEquals("Median doesn't match!", 50, stat.median(), 0);
        assertEquals("50 percentile doesn't match!", 51, stat.percentile(50),
                0);
        assertEquals("90 percentile doesn't match!", 91, stat.percentile(90),
                0);
        assertEquals("95 percentile doesn't match!", 96, stat.percentile(95),
                0);
        assertEquals("96 percentile doesn't match!", 97, stat.percentile(96),
                0);
        assertEquals("97 percentile doesn't match!", 98, stat.percentile(97),
                0);
        assertEquals("98 percentile doesn't match!", 99, stat.percentile(98),
                0);
        assertEquals("99 percentile doesn't match!", 100, stat.percentile(99),
                0);
        assertEquals("Standard deviation doesn't match!", 28.86607004772212,
                stat.standardDeviation(), 0);
        assertEquals("Standard deviation doesn't match!", 833.25,
                stat.variance(), 0);
    }

    @Test
    public void statsEvenLongValues() {
        final List<Long> values = new ArrayList<>();
        for (long i = 100; i > 0; i--) {
            values.add(i);
        }
        StatisticsListProvider<Long> stat = StatisticsListProviderFactory
                .longValues(values);
        assertEquals("Min doesn't match!", 1, stat.min(), 0);
        assertEquals("Max doesn't match!", 100, stat.max(), 0);
        assertEquals("Mean doesn't match!", 50.5, stat.mean(), 0);
        assertEquals("Median doesn't match!", 50, stat.median(), 0);
        assertEquals("50 percentile doesn't match!", 51, stat.percentile(50),
                0);
        assertEquals("90 percentile doesn't match!", 91, stat.percentile(90),
                0);
        assertEquals("95 percentile doesn't match!", 96, stat.percentile(95),
                0);
        assertEquals("96 percentile doesn't match!", 97, stat.percentile(96),
                0);
        assertEquals("97 percentile doesn't match!", 98, stat.percentile(97),
                0);
        assertEquals("98 percentile doesn't match!", 99, stat.percentile(98),
                0);
        assertEquals("99 percentile doesn't match!", 100, stat.percentile(99),
                0);
        assertEquals("Standard deviation doesn't match!", 28.86607004772212,
                stat.standardDeviation(), 0);
        assertEquals("Standard deviation doesn't match!", 833.25,
                stat.variance(), 0);
    }

    @Test
    public void statsOddLongValues() {
        final List<Long> values = new ArrayList<>();
        for (long i = 100; i > -1; i--) {
            values.add(i);
        }
        StatisticsListProvider<Long> stat = StatisticsListProviderFactory
                .longValues(values);
        assertEquals("Min doesn't match!", 0, stat.min(), 0);
        assertEquals("Max doesn't match!", 100, stat.max(), 0);
        assertEquals("Mean doesn't match!", 50, stat.mean(), 0);
        assertEquals("Median doesn't match!", 50, stat.median(), 0);
        assertEquals("50 percentile doesn't match!", 50, stat.percentile(50),
                0);
        assertEquals("90 percentile doesn't match!", 90, stat.percentile(90),
                0);
        assertEquals("95 percentile doesn't match!", 95, stat.percentile(95),
                0);
        assertEquals("96 percentile doesn't match!", 96, stat.percentile(96),
                0);
        assertEquals("97 percentile doesn't match!", 97, stat.percentile(97),
                0);
        assertEquals("98 percentile doesn't match!", 98, stat.percentile(98),
                0);
        assertEquals("99 percentile doesn't match!", 99, stat.percentile(99),
                0);
        assertEquals("Standard deviation doesn't match!", 29.154759474226502,
                stat.standardDeviation(), 0);
        assertEquals("Standard deviation doesn't match!", 850.0,
                stat.variance(), 0);
    }

    @Test
    public void mean() {
        StatisticsCalculator stat = StatisticsCalculator
                .fromValues(sampleList());
        assertEquals("Mean doesn't match!", 394.00, stat.mean(), 0);
    }

    @Test
    public void median() {
        StatisticsCalculator stat = StatisticsCalculator
                .fromValues(sampleList());
        assertEquals("Median doesn't match!", 430, stat.median(), 0);
    }

    @Test
    public void standardDeviation() {
        StatisticsCalculator stat = StatisticsCalculator
                .fromValues(sampleList());
        assertEquals("Standard deviation doesn't match!", 147.32277488562318,
                stat.standardDeviation(), 0);
    }

    @Test
    public void variance() {
        StatisticsCalculator stat = StatisticsCalculator
                .fromValues(sampleList());
        assertEquals("Variance doesn't match!", 21704, stat.variance(), 0);
    }

    @Test
    public void varianceAndStdOfKnownSamples() {
        List<Integer> values = new ArrayList<>();
        values.add(99);
        values.add(100);
        values.add(96);
        values.add(100);
        values.add(106);
        values.add(102);
        values.add(98);
        StatisticsCalculator stat = StatisticsCalculator.fromValues(values);
        assertEquals("Variance doesn't match!", 8.693877551020424,
                stat.variance(), 0);
        assertEquals("Standard deviation doesn't match!", 2.948538205792902,
                stat.standardDeviation(), 0);
    }

    @Test
    public void varianceAndStdOfKnownEggWeights() {
        List<Integer> values = new ArrayList<>();
        values.add(60);
        values.add(56);
        values.add(61);
        values.add(68);
        values.add(51);
        values.add(53);
        values.add(69);
        values.add(54);
        StatisticsCalculator stat = StatisticsCalculator.fromValues(values);
        assertEquals("Mean doesn't match!", 59, stat.mean(), 0);
        assertEquals("Standard deviation doesn't match!", 6.32455532033676,
                stat.standardDeviation(), 0);
        assertEquals("Variance doesn't match!", 40.000000000000014,
                stat.variance(), 0);
    }

    @Test
    public void varianceAndStdOfASampleTest() {
        StdOfASample stdOfASample = new StdOfASample();
        stdOfASample.add(99);
        stdOfASample.add(100);
        stdOfASample.add(96);
        stdOfASample.add(100);
        stdOfASample.add(106);
        stdOfASample.add(102);
        stdOfASample.add(98);

        assertEquals("Standard deviation doesn't match!", 3.184785258515421,
                stdOfASample.standardDeviation(), 0);
        assertEquals("Variance doesn't match!", 10.142857142857135,
                stdOfASample.variance(), 0);
    }

    final static class StdOfASample extends AbstractStandardDeviationProvider {

        private final List<Integer> values = new ArrayList<>();

        @Override
        public double standardDeviation() {
            return standardDeviationOf(StdFunction.stdOfASample);
        }

        public void add(int value) {
            values.add(value);
        }

        @Override
        protected double variance(StdFunction stdFunction) {
            return calculateVariance(values, stdFunction,
                    (value, mean) -> value - mean);
        }

        @Override
        public double variance() {
            return variance(StdFunction.stdOfASample);
        }
    }

    @Test
    public void empty() {
        final StatisticsCalculator empty = StatisticsCalculator
                .fromValues(new ArrayList<Integer>());
        assertEquals(0, empty.max(), 0);
        assertEquals(0, empty.min(), 0);
        assertEquals(0, empty.mean(), 0);
        assertEquals(0, empty.median(), 0);
        assertEquals(0, empty.percentile(90), 0);
        assertEquals(0, empty.percentile(95), 0);
        assertEquals(0, empty.percentile(96), 0);
        assertEquals(0, empty.percentile(97), 0);
        assertEquals(0, empty.percentile(98), 0);
        assertEquals(0, empty.percentile(99), 0);
        assertEquals(Double.NaN, empty.variance(), 0);
        assertEquals(Double.NaN, empty.standardDeviation(), 0);
    }

    private static List<Integer> sampleList() {
        final List<Integer> list = new ArrayList<>();
        list.add(600);
        list.add(470);
        list.add(170);
        list.add(430);
        list.add(300);
        return list;
    }

    @Test
    public void percentileTest() {
        final List<Integer> list = new ArrayList<>();
        list.add(15);
        list.add(20);
        list.add(35);
        list.add(40);
        list.add(50);
        final StatisticsCalculator stat = StatisticsCalculator.fromValues(list);
        assertEquals(20, stat.percentile(30), 0);
        assertEquals(20, stat.percentile(35), 0);
        assertEquals(35, stat.percentile(40), 0);
        assertEquals(50, stat.percentile(100), 0);
    }

    @Test
    public void performance() {
        final List<Integer> values = new ArrayList<>();
        for (int i = 1000000; i > -1; i--) {
            values.add(i);
        }
        final StatisticsCalculator stat = StatisticsCalculator
                .fromValues(values);
        assertEquals("Min doesn't match!", 0, stat.min(), 0);
        assertEquals("Max doesn't match!", 1000000, stat.max(), 0);
        assertEquals("Median doesn't match!", 500000, stat.median(), 0);
        assertEquals("50 percentile doesn't match!", 500000,
                stat.percentile(50), 0);
        assertEquals("90 percentile doesn't match!", 900000,
                stat.percentile(90), 0);
        assertEquals("95 percentile doesn't match!", 950000,
                stat.percentile(95), 0);
        assertEquals("96 percentile doesn't match!", 960000,
                stat.percentile(96), 0);
        assertEquals("97 percentile doesn't match!", 970000,
                stat.percentile(97), 0);
        assertEquals("98 percentile doesn't match!", 980000,
                stat.percentile(98), 0);
        assertEquals("99 percentile doesn't match!", 990000,
                stat.percentile(99), 0);
        assertEquals("Mean doesn't match!", 500000, stat.mean(), 0);
        assertEquals("Standard deviation doesn't match!", 288675.4232698031,
                stat.standardDeviation(), 0);
        assertEquals("Standard deviation doesn't match!", 8.333349999999998E10,
                stat.variance(), 0);
    }
}
