package org.fluentjava.volundr.statistics;

public final class PercentileRankCalculator {
    /**
     * Calculate nearest rank.
     * <p>
     *
     * http://en.wikipedia.org/wiki/Percentile
     *
     * @param percentile
     *            percentile (0-100)
     * @param sampleCount
     *            number of samples
     */
    public static double nearestRank(final int percentile,
            final long sampleCount) {
        return percentile * sampleCount / 100.0000 + 0.5000;
    }

}
