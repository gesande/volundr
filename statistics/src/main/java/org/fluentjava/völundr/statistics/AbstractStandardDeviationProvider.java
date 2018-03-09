package org.fluentjava.völundr.statistics;

import java.util.function.BiFunction;

public abstract class AbstractStandardDeviationProvider
        implements StandardDeviationProvider {

    protected enum StdFunction {
        stdOfAPopulation {
            @Override
            BiFunction<Double, Long, Double> std() {
                return (s, n) -> (s / n);
            }
        },
        stdOfASample {
            @Override
            BiFunction<Double, Long, Double> std() {
                return (s, n) -> (s / (n - 1));
            }
        };

        abstract BiFunction<Double, Long, Double> std();
    }

    /**
     * This can either be standard deviation of a population or a sample
     * depending on which StdFunction is used in the implementation of
     * calculating a variance.
     */
    @Override
    public abstract double standardDeviation();

    public final double standardDeviationOf(StdFunction func) {
        return Math.sqrt(variance(func));
    }

    protected abstract double variance(StdFunction stdFunction);

}
