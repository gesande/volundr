package org.fluentjava.volundr.graph;

public interface ImageFactory {

    void createXYLineChart(final String id, final ImageData imageData);

    void createBarChart(final String id, final ImageData imageData);

    void createScatterPlot(final String id, final ImageData imageData);

}
