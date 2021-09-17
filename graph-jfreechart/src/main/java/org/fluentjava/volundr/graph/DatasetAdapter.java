package org.fluentjava.volundr.graph;

public interface DatasetAdapter<T, PAINT> {
    void add(final Number x, final Number y);

    T graphData(final PAINT paint, final double range);
}
