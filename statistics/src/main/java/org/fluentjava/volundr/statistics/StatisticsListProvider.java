package org.fluentjava.volundr.statistics;

import java.util.List;
import java.util.function.Consumer;

public final class StatisticsListProvider<T extends Number & Comparable<T>>
        extends AbstractStandardDeviationProvider
        implements MaxValueProvider<T>, MinValueProvider<T>,
        MeanProvider<Double>, MedianProvider<T>, PercentileProvider<T> {
    private final List<T> values;
    private final NumberValueProvider<T> provider;
    private final StdFunction stdFunction;

    private StatisticsListProvider(final List<T> values,
            NumberValueProvider<T> provider, StdFunction stdFunction) {
        super();
        this.values = values;
        this.provider = provider;
        this.stdFunction = stdFunction;
    }

    public static <E extends Number & Comparable<E>> StatisticsListProvider<E> fromValues(
            final List<E> values, final NumberValueProvider<E> provider) {
        return new StatisticsListProvider<>(provider.sort(values), provider,
                StdFunction.stdOfAPopulation);
    }

    @Override
    public T percentile(final int percentile) {
        final long nearestRank = Rank.nearestRankOf(percentile,
                values().size());
        final int index = (int) (nearestRank - 1);
        return values().isEmpty() ? this.provider.zero()
                : values().get(
                        index >= values().size() ? values().size() - 1 : index);
    }

    /**
     * This will return standard deviation of a population/sample depending on
     * stdFunction passed in constructor
     */
    @Override
    public double standardDeviation() {
        return standardDeviationOf(stdFunction());
    }

    @Override
    public T median() {
        return values().isEmpty() ? zero() : this.provider.median(values());
    }

    @Override
    public Double mean() {
        if (values().isEmpty()) {
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
        return variance(stdFunction());
    }

    @Override
    protected double variance(StdFunction func) {
        return calculateVariance(values(), func, provider::delta);
    }

    @Override
    public T max() {
        return values().isEmpty() ? zero() : values().get(values().size() - 1);
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

    public void accept(Consumer<? super T> consumer) {
        this.values.forEach(consumer);
    }

    public int samples() {
        return this.values.size();
    }

    public boolean hasSamples() {
        return !values().isEmpty();
    }

    private StdFunction stdFunction() {
        return stdFunction;
    }
}
