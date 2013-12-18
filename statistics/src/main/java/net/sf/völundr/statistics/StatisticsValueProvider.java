package net.sf.völundr.statistics;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import net.sf.völundr.bag.StronglyTypedSortedBag;

public class StatisticsValueProvider implements PercentileProvider<Integer>,
		MaxValueProvider<Integer>, MinValueProvider<Integer>,
		MeanProvider<Double>, MedianProvider<Integer> {

	private final StronglyTypedSortedBag<Integer> latencies;
	private long totalLatency;
	private List<Integer> valuesToList;

	public StatisticsValueProvider() {
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
		if (this.valuesToList == null) {
			this.valuesToList = valuesToList();
		}
		final double nearestRank = PercentileRankCalculator.nearestRank(
				percentile, sampleCount());
		final long rounded = Math.round(nearestRank);
		final int index = (int) (rounded - 1);
		return this.valuesToList
				.get((int) (index >= sampleCount() ? sampleCount() - 1 : index));
	}

	@Override
	public Double mean() {
		return hasSamples() ? this.totalLatency / (double) sampleCount()
				: 0.0000;
	}

	@Override
	public Integer median() {
		if (this.valuesToList == null) {
			this.valuesToList = valuesToList();
		}
		return hasSamples() ? MedianResolver.resolveFrom(this.valuesToList) : 0;
	}

	private List<Integer> valuesToList() {
		final List<Integer> values = new ArrayList<Integer>();
		final Collection<Integer> uniqueSamples = latencies().uniqueSamples();
		for (final Integer value : uniqueSamples) {
			int count = countFor(value);
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
