package org.fluentjava.völundr.statistics;

import java.util.function.BiFunction;

public abstract class AbstractStandardDeviationProvider
        implements StandardDeviationProvider, VarianceProvider {

    /**
     * This can either be standard deviation of a population or a sample
     * depending on whether std or stdOfASample is used in the implementation of
     * variance
     */
    @Override
    public double standardDeviation() {
        return Math.sqrt(variance());
    }

    public final double standardDeviationOfPopulation() {
        return Math.sqrt(variance(std()));
    }

    public final double standardDeviationOfSample() {
        return Math.sqrt(variance(stdOfASample()));
    }

    protected abstract double variance(BiFunction<Double, Long, Double> std);

    protected static BiFunction<Double, Long, Double> std() {
        return (s, n) -> (s / n);
    }

    protected static BiFunction<Double, Long, Double> stdOfASample() {
        return (s, n) -> (s / (n - 1));
    }
}
