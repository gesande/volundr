package org.fluentjava.völundr.graph.scatterplot;

import org.fluentjava.völundr.graph.ImageData;

public interface ScatterPlotData {

    ImageData imageData();

    boolean hasSamples();

    void report(Number x, Number y);

}
