package org.fluentjava.völundr.graph.jfreechart;

import org.jfree.data.xy.XYSeries;

public class XYSeriesFactory {

    public XYSeries newXYSeries(final String legend) {
        return new XYSeries(legend, false, true);
    }
}
