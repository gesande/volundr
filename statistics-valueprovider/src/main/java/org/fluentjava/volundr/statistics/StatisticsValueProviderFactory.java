package org.fluentjava.volundr.statistics;

import org.fluentjava.volundr.bag.StronglyTypedSortedBag;

public final class StatisticsValueProviderFactory {

    public static AbstractStatisticsValueProvider<Integer> integerValues() {
        return new StatisticsValueProvider();
    }

    public static AbstractStatisticsValueProvider<Long> longValues() {
        return new AbstractStatisticsValueProvider<Long>(
                StronglyTypedSortedBag.synchronizedTreeBag()) {

            @Override
            protected Long zero() {
                return 0L;
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
