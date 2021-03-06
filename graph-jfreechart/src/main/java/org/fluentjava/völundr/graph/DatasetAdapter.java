package org.fluentjava.völundr.graph;

public interface DatasetAdapter<T, PAINT> {
    public void add(final Number x, final Number y);

    T graphData(final PAINT paint, final double range);
}
