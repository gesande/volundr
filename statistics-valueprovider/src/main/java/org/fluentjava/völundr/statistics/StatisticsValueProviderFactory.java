package org.fluentjava.völundr.statistics;

import org.fluentjava.völundr.bag.StronglyTypedSortedBag;

public final class StatisticsValueProviderFactory {

    public static AbstractStatisticsValueProvider<Integer> integerValues() {
        return new StatisticsValueProvider();
    }

    public static AbstractStatisticsValueProvider<Long> longValues() {
        return new AbstractStatisticsValueProvider<Long>(
                StronglyTypedSortedBag.<Long> synchronizedTreeBag()) {

            @Override
            protected Long zero() {
                return Long.valueOf(0);
            }

            @Override
            protected boolean equalsToZero(Long value) {
                return value == 0;
            }

            @Override
            protected Long calculateMean(Long value1, Long value2) {
                return (value1 + value2) / 2;
            }

            @Override
            protected void addToTotal(Long value) {
                this.total += value;
            }
        };
    }
}
