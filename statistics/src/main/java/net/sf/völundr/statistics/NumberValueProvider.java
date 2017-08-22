package net.sf.völundr.statistics;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SuppressWarnings("rawtypes")
public abstract class NumberValueProvider<T extends Number & Comparable> {

    /**
     * Zero value
     */
    abstract T zero();

    /**
     * Value's distance from given mean, basically <code>value - mean</code>
     */
    abstract double delta(T value, double mean);

    /**
     * Sorting a list of items
     */
    abstract List<T> sort(List<T> list);

    /**
     * Plus operation
     */
    abstract T plus(T value1, T value2);

    /**
     * Calculate mean value of a list of items
     */
    abstract T median(List<T> values);

    /**
     * Divide operation
     */
    abstract double divide(long sum, int size);

    /**
     * Calculates sum of a list of items
     */
    abstract long sum(List<T> values);
}

final class IntegerNumberProvider extends NumberValueProvider<Integer> {

    @Override
    Integer zero() {
        return Integer.valueOf(0);
    }

    @Override
    double delta(Integer value, double mean) {
        return value - mean;
    }

    @Override
    List<Integer> sort(List<Integer> list) {
        List<Integer> sorted = new ArrayList<>(list);
        Collections.sort(sorted);
        return sorted;
    }

    @Override
    Integer plus(Integer value1, Integer value2) {
        return value1 + value2;
    }

    @Override
    Integer median(List<Integer> values) {
        return MedianResolver.resolveFrom(values);
    }

    @Override
    double divide(long sum, int size) {
        return (double) sum / size;
    }

    @Override
    long sum(List<Integer> values) {
        long sum = 0;
        for (final Integer latency : values) {
            sum += latency;
        }
        return sum;
    }

}

final class LongNumberProvider extends NumberValueProvider<Long> {

    @Override
    Long zero() {
        return Long.valueOf(0);
    }

    @Override
    double delta(Long value, double mean) {
        return value - mean;
    }

    @Override
    List<Long> sort(List<Long> list) {
        List<Long> sorted = new ArrayList<>(list);
        Collections.sort(sorted);
        return sorted;
    }

    @Override
    Long plus(Long value1, Long value2) {
        return value1 + value2;
    }

    @Override
    Long median(List<Long> values) {
        return MedianResolver.resolveFromLong(values);
    }

    @Override
    double divide(long sum, int size) {
        return (double) sum / size;
    }

    @Override
    long sum(List<Long> values) {
        long sum = 0;
        for (final Long latency : values) {
            sum += latency;
        }
        return sum;
    }
}

@SuppressWarnings("rawtypes")
final class StatisticsListProvider<T extends Number & Comparable>
        extends AbstractStandardDeviationProvider
        implements MaxValueProvider<T>, MinValueProvider<T>,
        MeanProvider<Double>, MedianProvider<T>, PercentileProvider<T> {
    private final List<T> values;
    private final NumberValueProvider<T> provider;

    protected StatisticsListProvider(final List<T> values,
            NumberValueProvider<T> provider) {
        this.values = values;
        this.provider = provider;
    }

    public static <E extends Number & Comparable, MEAN extends Number> StatisticsListProvider<E> fromValues(
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
}
