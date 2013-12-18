package net.sf.völundr.statistics;

public interface PercentileProvider<TYPE> {

	TYPE percentile(final int percentile);

}
