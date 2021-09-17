package org.fluentjava.volundr.graph.jfreechart.api;

import java.util.Collection;
import java.util.concurrent.atomic.AtomicInteger;

import org.fluentjava.volundr.bag.StronglyTypedSortedBag;
import org.fluentjava.volundr.graph.ImageData;
import org.fluentjava.volundr.graph.ImageDataFactory;
import org.fluentjava.volundr.graph.ImageFactory;
import org.fluentjava.volundr.graph.jfreechart.DefaultDatasetAdapterFactory;
import org.fluentjava.volundr.graph.jfreechart.ImageFactoryUsingJFreeChart;
import org.fluentjava.volundr.graph.jfreechart.JFreeChartWriter;
import org.fluentjava.volundr.graph.scatterplot.ScatterPlotBuilder;
import org.fluentjava.volundr.graph.scatterplot.ScatterPlotData;
import org.fluentjava.volundr.statistics.AbstractStatisticsValueProvider;
import org.fluentjava.volundr.statistics.StatisticsListProvider;

public class ChartGraphApi {
    private final static DefaultDatasetAdapterFactory ADAPTER_FACTORY = new DefaultDatasetAdapterFactory();
    private final ImageFactory imageFactory;
    private final FrequencyGraphApi frequencyGraphApi;

    public ChartGraphApi(String targetPath) {
        this.imageFactory = new ImageFactoryUsingJFreeChart(
                new JFreeChartWriter(targetPath));
        this.frequencyGraphApi = new FrequencyGraphApi(imageFactory,
                ADAPTER_FACTORY);
    }

    public <T extends Number & Comparable<T>> void createLineChart(
            String legendTitle, String graphTitle, String xAxisTitle,
            String graphName, StatisticsListProvider<T> statistics) {
        final ImageDataFactory imageDataFactory = new ImageDataFactory(
                ADAPTER_FACTORY);
        ImageData imageData = imageDataFactory
                .newImageDataForLineChart(legendTitle, graphTitle, xAxisTitle)
                .range(statistics.max().doubleValue());
        AtomicInteger i = new AtomicInteger(0);
        statistics.accept(value -> {
            imageData.add(i.get(), value);
            i.incrementAndGet();
        });
        if (statistics.hasSamples()) { // no need to create an empty graph
            imageFactory.createXYLineChart(graphName, imageData);
        }
    }

    public <T extends Number & Comparable<T>> void createBarChart(
            String legendTitle, String graphTitle, String xAxisTitle,
            String graphName, StronglyTypedSortedBag<T> values) {
        final ImageData data = ImageData.noStatistics(graphTitle, xAxisTitle,
                ADAPTER_FACTORY.forBarChart(legendTitle));
        final Collection<T> samples = values.uniqueSamples();
        for (final T sample : samples) {
            data.add(values.count(sample), sample);
        }
        data.range(values.size());
        if (!samples.isEmpty()) { // no need to create an empty graph
            imageFactory.createBarChart(graphName, data);
        }
    }

    public <T extends Number & Comparable<T>> void createBarChart(
            String legendTitle, String graphTitle, String xAxisTitle,
            String graphName, AbstractStatisticsValueProvider<T> statistics) {
        final ImageData data = ImageData.noStatistics(graphTitle, xAxisTitle,
                ADAPTER_FACTORY.forBarChart(legendTitle));
        statistics.acceptUniqueSamples(
                sample -> data.add(statistics.countFor(sample), sample));
        data.range(statistics.sampleCount());
        if (statistics.hasSamples()) { // no need to create an empty graph
            imageFactory.createBarChart(graphName, data);
        }
    }

    public ScatterPlotData createScatterPlotData(String graphTitle,
            String xAxisTitle, String yAxisTitle, String legendTitle) {
        return ScatterPlotBuilder.newScatterPlotData(graphTitle, xAxisTitle,
                yAxisTitle, legendTitle, ADAPTER_FACTORY);
    }

    public void createScatterPlot(ScatterPlotData scatterPlotData,
            String graphName) {
        ScatterPlotBuilder.newScatterPlotBuilder(scatterPlotData)
                .writeGraph(imageFactory, graphName);
    }

    public void createFrequencyGraph(String graphTitle, String xAxisTitle,
            String graphName, StatisticsListProvider<Integer> statistics) {
        frequencyGraphApi.createFrequencyGraph(graphTitle, xAxisTitle,
                graphName, statistics);
    }

    public void createFrequencyGraphLongValues(String graphTitle,
            String xAxisTitle, String graphName,
            StatisticsListProvider<Long> statistics) {
        frequencyGraphApi.createFrequencyGraphLongValues(graphTitle, xAxisTitle,
                graphName, statistics);
    }

}
