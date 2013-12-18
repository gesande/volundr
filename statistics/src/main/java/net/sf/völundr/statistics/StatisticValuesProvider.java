package net.sf.völundr.statistics;

import java.util.ArrayList;
import java.util.List;

import net.sf.völundr.bag.StronglyTypedSortedBag;

public class StatisticValuesProvider implements PercentileProvider<Integer>,
		MaxValueProvider<Integer>, MinValueProvider<Integer>,
		MeanProvider<Double>, MedianProvider<Integer> {

	private final StronglyTypedSortedBag<Integer> latencies;
	private long totalLatency;

	public StatisticValuesProvider() {
		this.latencies = StronglyTypedSortedBag.<Integer> synchronizedTreeBag();
	}

	public boolean hasSamples() {
		return sampleCount() > 0;
	}

	public synchronized void addSample(final int latency) {
		latencies().add(latency);
		this.totalLatency += latency;
	}

	public int countFor(final int latency) {
		return latencies().count(latency);
	}

	private StronglyTypedSortedBag<Integer> latencies() {
		return this.latencies;
	}

	@Override
	public Integer min() {
		return hasSamples() ? findFirst() : 0;
	}

	@Override
	public Integer max() {
		return hasSamples() ? findLast() : 0;
	}

	public long sampleCount() {
		return latencies().size();
	}

	@Override
	public Integer percentile(final int percentile) {
		if (!hasSamples()) {
			return 0;
		}
		final double targetCount = PercentileRankCalculator.calculate(
				percentile, sampleCount());
		long count = 0;
		for (int value = min(); value <= max(); value++) {
			count += countFor(value);
			if (count >= targetCount) {
				return value;
			}
		}
		return max();
	}

	@Override
	public Double mean() {
		return hasSamples() ? this.totalLatency / sampleCount() : 0.0;
	}

	@Override
	public Integer median() {
		return hasSamples() ? MedianCalculator.calculateFrom(valuesToList())
				: 0;
	}

	private List<Integer> valuesToList() {
		final List<Integer> values = new ArrayList<Integer>();
		for (int value = min(); value <= max(); value++) {
			final int count = countFor(value);
			for (int i = 0; i < count; i++) {
				values.add(value);
			}
		}
		return values;
	}

	private Integer findFirst() {
		return latencies().findFirst();
	}

	private Integer findLast() {
		return latencies().findLast();
	}
}
