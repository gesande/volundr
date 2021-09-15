package org.fluentjava.volundr.statistics;

import org.fluentjava.volundr.bag.StronglyTypedSortedBag;

public class StatisticsValueProvider
        extends AbstractStatisticsValueProvider<Integer> {

    public StatisticsValueProvider() {
        super(StronglyTypedSortedBag.<Integer> synchronizedTreeBag());
    }

    @Override
    protected void addToTotal(Integer value) {
        this.total += value;
    }

    @Override
    protected Integer zero() {
        return Integer.valueOf(0);
    }

    @Override
    protected boolean equalsToZero(Integer value) {
        return value == 0;
    }

    @Override
    protected Integer calculateMean(Integer value1, Integer value2) {
        return (value1 + value2) / 2;
    }

}
