package org.fluentjava.volundr.statistics;

public interface PercentileProvider<TYPE> {

    TYPE percentile(final int percentile);

}
