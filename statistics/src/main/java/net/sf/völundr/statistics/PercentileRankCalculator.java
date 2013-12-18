package net.sf.völundr.statistics;

public final class PercentileRankCalculator {

	public static double calculate(final int percentile, final long sampleCount) {
		return (percentile * sampleCount / 100 + 0.5);
	}

}
