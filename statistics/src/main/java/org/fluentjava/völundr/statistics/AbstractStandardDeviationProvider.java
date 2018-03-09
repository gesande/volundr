package org.fluentjava.völundr.statistics;

public abstract class AbstractStandardDeviationProvider
        implements StandardDeviationProvider, VarianceProvider {

    /**
     * This can either be standard deviation of a population or a sample
     * depending on whether std or stdOfASample is used in the implementation of
     * variance
     */
    @Override
    public final double standardDeviation() {
        return Math.sqrt(variance());
    }

    protected static final double std(final double s, final long n) {
        return (s / n);
    }

    protected static final double stdOfASample(final double s, final long n) {
        return (s / (n - 1));
    }

}
