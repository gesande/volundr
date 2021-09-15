package org.fluentjava.volundr.graph.jfreechart;

import java.awt.Paint;

import org.fluentjava.volundr.graph.BarChartAdapterProvider;
import org.fluentjava.volundr.graph.DatasetAdapter;
import org.fluentjava.volundr.graph.LineChartAdapterProvider;
import org.fluentjava.volundr.graph.ScatterPlotAdapterProvider;

public interface DatasetAdapterFactory
        extends LineChartAdapterProvider<LineChartGraphData, Paint>,
        BarChartAdapterProvider<BarChartGraphData, Paint>,
        ScatterPlotAdapterProvider<ScatterPlotGraphData, Paint> {

    @Override
    DatasetAdapter<LineChartGraphData, Paint> forLineChart(final String title);

    @Override
    DatasetAdapter<BarChartGraphData, Paint> forBarChart(
            final String legendTitle);

    @Override
    DatasetAdapter<ScatterPlotGraphData, Paint> forScatterPlot(
            final String legendTitle, final String yAxisTitle);

}
