package net.sf.völundr.statistics;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SuppressWarnings("rawtypes")
abstract class NumberValueProvider<T extends Number & Comparable> {

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
