package net.sf.völundr.statistics;

public abstract class AbstractStandardDeviationProvider implements
		StandardDeviationProvider, VarianceProvider {

	@Override
	public final double standardDeviation() {
		return Math.sqrt(variance());
	}

	protected static final double std(final double s, final long n) {
		return (s / n);
	}

	protected static final double stdOfASample(final double s, final long n) {
		return (s / (n - 1));
	}

}
