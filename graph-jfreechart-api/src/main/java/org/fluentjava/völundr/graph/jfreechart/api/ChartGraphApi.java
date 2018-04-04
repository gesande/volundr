package org.fluentjava.völundr.graph.jfreechart.api;

import java.util.Collection;
import java.util.concurrent.atomic.AtomicInteger;

import org.fluentjava.völundr.bag.StronglyTypedSortedBag;
import org.fluentjava.völundr.graph.ImageData;
import org.fluentjava.völundr.graph.ImageDataFactory;
import org.fluentjava.völundr.graph.jfreechart.DefaultDatasetAdapterFactory;
import org.fluentjava.völundr.graph.jfreechart.ImageFactoryUsingJFreeChart;
import org.fluentjava.völundr.graph.jfreechart.JFreeChartWriter;
import org.fluentjava.völundr.statistics.AbstractStatisticsValueProvider;
import org.fluentjava.völundr.statistics.StatisticsListProvider;

public class ChartGraphApi {
    private final static DefaultDatasetAdapterFactory ADAPTER_FACTORY = new DefaultDatasetAdapterFactory();
    private final ImageFactoryUsingJFreeChart imageFactory;

    public ChartGraphApi(String targetPath) {
        imageFactory = new ImageFactoryUsingJFreeChart(
                new JFreeChartWriter(targetPath));
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
        statistics.acceptUniqueSamples(sample -> {
            data.add(statistics.countFor(sample), sample);
        });
        data.range(statistics.sampleCount());
        if (statistics.hasSamples()) { // no need to create an empty graph
            imageFactory.createBarChart(graphName, data);
        }
    }

}
