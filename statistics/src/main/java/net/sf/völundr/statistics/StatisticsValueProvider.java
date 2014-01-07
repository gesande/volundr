package net.sf.völundr.statistics;

import net.sf.völundr.bag.StronglyTypedSortedBag;

public class StatisticsValueProvider implements PercentileProvider<Integer>,
		MaxValueProvider<Integer>, MinValueProvider<Integer>,
		MeanProvider<Double>, MedianProvider<Integer> {

	private final StronglyTypedSortedBag<Integer> values;
	private long totalLatency;

	public StatisticsValueProvider() {
		this.values = StronglyTypedSortedBag.<Integer> synchronizedTreeBag();
	}

	public boolean hasSamples() {
		return sampleCount() > 0;
	}

	public synchronized void addSample(final int latency) {
		values().add(latency);
		this.totalLatency += latency;
	}

	public int countFor(final int latency) {
		return values().count(latency);
	}

	private StronglyTypedSortedBag<Integer> values() {
		return this.values;
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
		return values().size();
	}

	@Override
	public Integer percentile(final int percentile) {
		if (!hasSamples()) {
			return 0;
		}
		final double nearestRank = PercentileRankCalculator.nearestRank(
				percentile, sampleCount());
		final long rounded = Math.round(nearestRank);
		final long index = rounded - 1;
		return valueFor(index);
	}

	private Integer valueFor(final long index) {
		int sum = -1;
		for (final Integer value : values().uniqueSamples()) {
			final int count = countFor(value);
			sum += count;
			if (index <= sum) {
				return value;
			}
		}
		return max();
	}

	@Override
	public Double mean() {
		return hasSamples() ? this.totalLatency / (double) sampleCount()
				: 0.0000;
	}

	@Override
	public Integer median() {
		final long size = values().size();
		final long index = size / 2;
		if (size % 2 == 1) {
			return valueFor(index);
		}
		return valueFor(index, size / 2 - 1);
	}

	private Integer valueFor(final long index, final long index2) {
		int sum = -1;
		Integer lowerMiddle = 0;
		Integer upperMiddle = 0;
		for (final Integer value : values().uniqueSamples()) {
			final int count = countFor(value);
			sum += count;
			if (index2 <= sum && lowerMiddle == 0) {
				lowerMiddle = value;
			}
			if (index <= sum && upperMiddle == 0) {
				upperMiddle = value;
			}
		}
		return (lowerMiddle + upperMiddle) / 2;
	}

	private Integer findFirst() {
		return values().findFirst();
	}

	private Integer findLast() {
		return values().findLast();
	}
}
