package org.fluentjava.völundr.statistics;

import java.util.List;

@SuppressWarnings("rawtypes")
public final class StatisticsListProvider<T extends Number & Comparable>
        extends AbstractStandardDeviationProvider
        implements MaxValueProvider<T>, MinValueProvider<T>,
        MeanProvider<Double>, MedianProvider<T>, PercentileProvider<T> {
    private final List<T> values;
    private final NumberValueProvider<T> provider;

    private StatisticsListProvider(final List<T> values,
            NumberValueProvider<T> provider) {
        this.values = values;
        this.provider = provider;
    }

    public static <E extends Number & Comparable> StatisticsListProvider<E> fromValues(
            final List<E> values, final NumberValueProvider<E> provider) {
        return new StatisticsListProvider<>(provider.sort(values), provider);
    }

    @Override
    public T percentile(final int percentile) {
        final long rounded = round(nearestRank(percentile));
        final int index = (int) (rounded - 1);
        return values().isEmpty() ? this.provider.zero()
                : values().get(
                        index >= values().size() ? values().size() - 1 : index);
    }

    private static long round(final double nearestRank) {
        return Math.round(nearestRank);
    }

    private double nearestRank(final int percentile) {
        return PercentileRankCalculator.nearestRank(percentile,
                values().size());
    }

    @Override
    public T median() {
        return values().isEmpty() ? zero() : this.provider.median(values());
    }

    @Override
    public Double mean() {
        if (values().size() == 0) {
            return 0.0;
        }
        long sum = this.provider.sum(values());
        return this.provider.divide(sum, values().size());
    }

    /**
     * When this is used to calculate standard deviation, it'll be standard
     * deviation of a population
     */
    @Override
    public double variance() {
        long n = 0;
        double mean = 0;
        double s = 0.0;
        for (final T x : values()) {
            n++;
            final double delta = delta(x, mean);
            mean += delta / n;
            s += delta * delta(x, mean);
        }
        return std(s, n);
    }

    @Override
    public T max() {
        return values().isEmpty() ? zero() : values().get(values().size() - 1);
    }

    private double delta(final T x, double mean) {
        return this.provider.delta(x, mean);
    }

    private T zero() {
        return this.provider.zero();
    }

    @Override
    public T min() {
        return values().isEmpty() ? zero() : values().get(0);
    }

    private List<T> values() {
        return this.values;
    }

    public int samples() {
        return this.values.size();
    }
}
