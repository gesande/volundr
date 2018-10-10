package org.fluentjava.völundr.graph.jfreechart;

import java.awt.Color;
import java.awt.Font;

import org.fluentjava.völundr.graph.ImageData;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

final class JFreeChartFactory {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(ImageFactoryUsingJFreeChart.class);

    private static final String FONT_FAMILY_NAME = "Monospaced";
    private static final Font TITLE_FONT = new Font(FONT_FAMILY_NAME, Font.BOLD,
            20);
    private static final Font LEGEND_FONT = new Font(FONT_FAMILY_NAME,
            Font.PLAIN, 12);
    private static final Font LABEL_FONT = new Font(FONT_FAMILY_NAME,
            Font.PLAIN, 12);
    private static final Font DOMAIN_AXIS_FONT = new Font(FONT_FAMILY_NAME,
            Font.PLAIN, 12);
    private static final Font RANGE_AXIS_FONT = new Font(FONT_FAMILY_NAME,
            Font.PLAIN, 12);

    @SuppressWarnings("static-method")
    public JFreeChart newBarChart(final ImageData imageData) {
        LOGGER.info("Create bar chart...");
        JFreeChart barChart = ChartFactory.createBarChart(imageData.title(),
                imageData.xAxisLabel(), null, null, PlotOrientation.VERTICAL,
                showLegend(), noTooltips(), noUrls());
        barChart.getTitle().setFont(TITLE_FONT);
        barChart.getLegend().setItemFont(LEGEND_FONT);
        barChart.getCategoryPlot().getDomainAxis()
                .setTickLabelFont(DOMAIN_AXIS_FONT);
        barChart.getCategoryPlot().getDomainAxis().setLabelFont(LABEL_FONT);
        barChart.getCategoryPlot().getRangeAxis()
                .setTickLabelFont(RANGE_AXIS_FONT);
        barChart.getCategoryPlot().getRangeAxis().setLabelFont(LABEL_FONT);
        return barChart;
    }

    @SuppressWarnings("static-method")
    public JFreeChart newScatterPlot(final ImageData imageData) {
        LOGGER.info("Create scatter plot...");
        final XYSeriesAdapterForScatterPlot adapter = (XYSeriesAdapterForScatterPlot) imageData
                .adapter();
        final ScatterPlotGraphData graphData = adapter.graphData(Color.RED, 0);
        JFreeChart scatterPlot = ChartFactory.createScatterPlot(
                imageData.title(), imageData.xAxisLabel(),
                graphData.yAxisTitle(), graphData.dataset(),
                PlotOrientation.VERTICAL, showLegend(), noTooltips(), noUrls());
        scatterPlot.getTitle().setFont(TITLE_FONT);
        scatterPlot.getLegend().setItemFont(LEGEND_FONT);
        scatterPlot.getXYPlot().getDomainAxis().setLabelFont(LABEL_FONT);
        scatterPlot.getXYPlot().getDomainAxis()
                .setTickLabelFont(DOMAIN_AXIS_FONT);
        scatterPlot.getXYPlot().getRangeAxis()
                .setTickLabelFont(RANGE_AXIS_FONT);
        scatterPlot.getXYPlot().getRangeAxis().setLabelFont(LABEL_FONT);
        return scatterPlot;
    }

    @SuppressWarnings("static-method")
    public JFreeChart newXYLineChart(final ImageData imageData) {
        LOGGER.info("Create XY linechart...");
        JFreeChart lineChart = ChartFactory.createXYLineChart(imageData.title(),
                imageData.xAxisLabel(), null, null, PlotOrientation.VERTICAL,
                showLegend(), noTooltips(), noUrls());
        lineChart.getTitle().setFont(TITLE_FONT);
        lineChart.getLegend().setItemFont(LEGEND_FONT);
        lineChart.getXYPlot().getDomainAxis()
                .setTickLabelFont(DOMAIN_AXIS_FONT);
        lineChart.getXYPlot().getDomainAxis().setLabelFont(LABEL_FONT);
        lineChart.getXYPlot().getRangeAxis().setTickLabelFont(RANGE_AXIS_FONT);
        lineChart.getXYPlot().getRangeAxis().setLabelFont(LABEL_FONT);
        return lineChart;
    }

    private static boolean showLegend() {
        return true;
    }

    private static boolean noTooltips() {
        return false;
    }

    private static boolean noUrls() {
        return false;
    }

}
