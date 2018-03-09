package org.fluentjava.völundr.statistics;

final class Rank {
    public static long nearestRankOf(int percentile, int size) {
        return Math.round(nearestRank(percentile, size));
    }

    private static double nearestRank(final int percentile, int size) {
        return PercentileRankCalculator.nearestRank(percentile, size);
    }
}
