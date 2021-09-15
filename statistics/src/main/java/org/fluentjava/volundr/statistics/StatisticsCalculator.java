package org.fluentjava.volundr.statistics;

import java.util.List;

public final class StatisticsCalculator implements MaxValueProvider<Integer>,
        MinValueProvider<Integer>, MeanProvider<Double>,
        MedianProvider<Integer>, PercentileProvider<Integer>,
        StandardDeviationProvider, VarianceProvider {

    private StatisticsListProvider<Integer> provider;

    private StatisticsCalculator(
            final StatisticsListProvider<Integer> provider) {
        this.provider = provider;
    }

    public static StatisticsCalculator fromValues(final List<Integer> values) {
        return new StatisticsCalculator(StatisticsListProvider
                .fromValues(values, new IntegerNumberProvider()));
    }

    @Override
    public Integer percentile(final int percentile) {
        return this.provider.percentile(percentile);
    }

    @Override
    public Integer median() {
        return this.provider.median();
    }

    @Override
    public Double mean() {
        return this.provider.mean();
    }

    @Override
    public double variance() {
        return this.provider.variance();
    }

    @Override
    public Integer max() {
        return this.provider.max();
    }

    @Override
    public Integer min() {
        return this.provider.min();
    }

    @Override
    public double standardDeviation() {
        return this.provider.standardDeviation();
    }
}
