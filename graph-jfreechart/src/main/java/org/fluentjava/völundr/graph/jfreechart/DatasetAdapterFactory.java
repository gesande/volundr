package org.fluentjava.völundr.graph.jfreechart;

import java.awt.Paint;

import org.fluentjava.völundr.graph.BarChartAdapterProvider;
import org.fluentjava.völundr.graph.DatasetAdapter;
import org.fluentjava.völundr.graph.LineChartAdapterProvider;
import org.fluentjava.völundr.graph.ScatterPlotAdapterProvider;

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
