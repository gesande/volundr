package org.fluentjava.volundr.graph.scatterplot;

import org.fluentjava.volundr.graph.ImageData;

public interface ScatterPlotData {

    ImageData imageData();

    boolean hasSamples();

    void report(Number x, Number y);

}
