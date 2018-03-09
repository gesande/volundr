package org.fluentjava.völundr.statistics;

import org.fluentjava.völundr.bag.StronglyTypedSortedBag;

@SuppressWarnings("rawtypes")
abstract class AbstractStatisticsValueProvider<T extends Number & Comparable>
        implements PercentileProvider<T>, MaxValueProvider<T>,
        MinValueProvider<T>, MeanProvider<Double>, MedianProvider<T> {
    private final StronglyTypedSortedBag<T> values;
    protected long total;

    protected AbstractStatisticsValueProvider(
            StronglyTypedSortedBag<T> values) {
        this.values = values;
    }

    public synchronized void addSample(final T value) {
        values().add(value);
        addToTotal(value);
    }

    protected abstract void addToTotal(T value);

    protected abstract T zero();

    public boolean hasSamples() {
        return sampleCount() > 0;
    }

    public long sampleCount() {
        return values().size();
    }

    private StronglyTypedSortedBag<T> values() {
        return this.values;
    }

    public int countFor(final T value) {
        return values().count(value);
    }

    @Override
    public T min() {
        return hasSamples() ? findFirst() : zero();
    }

    @Override
    public T max() {
        return hasSamples() ? findLast() : zero();
    }

    private T findFirst() {
        return values().findFirst();
    }

    private T findLast() {
        return values().findLast();
    }

    @Override
    public T percentile(final int percentile) {
        if (!hasSamples()) {
            return zero();
        }
        final double nearestRank = PercentileRankCalculator
                .nearestRank(percentile, sampleCount());
        final long rounded = Math.round(nearestRank);
        final long index = rounded - 1;
        return valueFor(index);
    }

    private T valueFor(final long index) {
        int sum = -1;
        for (final T value : values().uniqueSamples()) {
            final int count = countFor(value);
            sum += count;
            if (index <= sum) {
                return value;
            }
        }
        return max();
    }

    @Override
    public Double mean() {
        return hasSamples() ? this.total / (double) sampleCount() : 0.0000;
    }

    @Override
    public T median() {
        final long size = values().size();
        final long index = size / 2;
        if (size % 2 == 1) {
            return valueFor(index);
        }
        return valueFor(index, size / 2 - 1);
    }

    private T valueFor(final long index, final long index2) {
        int sum = -1;
        T lowerMiddle = zero();
        T upperMiddle = zero();
        for (final T value : values().uniqueSamples()) {
            final int count = countFor(value);
            sum += count;
            if (index2 <= sum && equalsToZero(lowerMiddle)) {
                lowerMiddle = value;
            }
            if (index <= sum && equalsToZero(upperMiddle)) {
                upperMiddle = value;
            }
        }
        return calculateMean(lowerMiddle, upperMiddle);
    }

    protected abstract boolean equalsToZero(T value);

    protected abstract T calculateMean(T value1, T value2);

}
