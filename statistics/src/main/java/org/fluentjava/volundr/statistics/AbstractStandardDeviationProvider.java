package org.fluentjava.volundr.statistics;

import java.util.List;
import java.util.function.BiFunction;

public abstract class AbstractStandardDeviationProvider
        implements StandardDeviationProvider, VarianceProvider {

    @FunctionalInterface
    protected interface DeltaFunction<T extends Number>
            extends BiFunction<T, Double, Double> {
        //
    }

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
     *
     * See https://en.wikipedia.org/wiki/Standard_deviation
     */
    @Override
    public abstract double standardDeviation();

    /**
     * Variance is the expectation of the squared deviation of a random variable
     * from its mean.
     *
     * Reference: https://en.wikipedia.org/wiki/Variance
     */
    @Override
    public abstract double variance();

    protected abstract double variance(StdFunction stdFunction);

    protected final double standardDeviationOf(StdFunction func) {
        return Math.sqrt(variance(func));
    }

    protected static <T extends Number> double calculateVariance(
            final List<T> values, final StdFunction func,
            final DeltaFunction<T> deltaFunc) {
        long n = 0;
        double mean = 0;
        double s = 0.0;
        for (final T value : values) {
            n++;
            final double delta = deltaFunc.apply(value, mean);
            mean += delta / n;
            s += delta * deltaFunc.apply(value, mean);
        }
        return func.std().apply(s, n);
    }

}
