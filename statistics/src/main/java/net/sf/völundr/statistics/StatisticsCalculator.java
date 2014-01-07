package net.sf.völundr.statistics;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class StatisticsCalculator extends
		AbstractStandardDeviationProvider implements MaxValueProvider<Integer>,
		MinValueProvider<Integer>, MeanProvider<Double>,
		MedianProvider<Integer>, PercentileProvider<Integer> {

	private final List<Integer> values;

	private StatisticsCalculator(final List<Integer> values) {
		this.values = values;
	}

	public static StatisticsCalculator fromValues(final List<Integer> values) {
		return new StatisticsCalculator(asSorted(values));
	}

	private static List<Integer> asSorted(List<Integer> latencies) {
		final List<Integer> sortedLatencies = new ArrayList<Integer>(latencies);
		Collections.sort(sortedLatencies);
		return sortedLatencies;
	}

	@Override
	public Integer percentile(final int percentile) {
		final long rounded = round(nearestRank(percentile));
		final int index = (int) (rounded - 1);
		return values().isEmpty() ? Integer.valueOf(0) : values().get(
				index >= values().size() ? values().size() - 1 : index);
	}

	private static long round(final double nearestRank) {
		return Math.round(nearestRank);
	}

	private double nearestRank(final int percentile) {
		return PercentileRankCalculator
				.nearestRank(percentile, values().size());
	}

	@Override
	public Integer median() {
		return values().isEmpty() ? 0 : MedianResolver.resolveFrom(values());
	}

	@Override
	public Double mean() {
		if (values().size() == 0) {
			return 0.0;
		}
		long sum = 0;
		for (final Integer latency : values()) {
			sum += latency;
		}
		return (double) sum / values().size();
	}

	@Override
	public double variance() {
		long n = 0;
		double mean = 0;
		double s = 0.0;
		for (final Integer x : values()) {
			n++;
			final double delta = x - mean;
			mean += delta / n;
			s += delta * (x - mean);
		}
		return std(s, n);
	}

	@Override
	public Integer max() {
		return values().isEmpty() ? Integer.valueOf(0) : values().get(
				values().size() - 1);
	}

	@Override
	public Integer min() {
		return values().isEmpty() ? Integer.valueOf(0) : values().get(0);
	}

	private List<Integer> values() {
		return this.values;
	}

}
