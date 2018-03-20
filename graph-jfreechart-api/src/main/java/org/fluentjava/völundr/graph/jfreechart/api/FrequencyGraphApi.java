package org.fluentjava.völundr.graph.jfreechart.api;

import org.fluentjava.völundr.bag.StronglyTypedSortedBag;
import org.fluentjava.völundr.graph.frequency.FrequencyData;
import org.fluentjava.völundr.graph.frequency.FrequencyGraphBuilder;
import org.fluentjava.völundr.graph.jfreechart.DefaultDatasetAdapterFactory;
import org.fluentjava.völundr.graph.jfreechart.ImageFactoryUsingJFreeChart;
import org.fluentjava.völundr.graph.jfreechart.JFreeChartWriter;
import org.fluentjava.völundr.statistics.StatisticsListProvider;

public final class FrequencyGraphApi {

    private final static DefaultDatasetAdapterFactory ADAPTER_FACTORY = new DefaultDatasetAdapterFactory();
    private final ImageFactoryUsingJFreeChart imageFactory;

    public FrequencyGraphApi(String targetPath) {
        imageFactory = new ImageFactoryUsingJFreeChart(
                new JFreeChartWriter(targetPath));
    }

    public void createFrequencyGraph(String graphTitle, String xAxisTitle,
            String graphName, StatisticsListProvider<Integer> statistics) {
        final StronglyTypedSortedBag<Integer> bag = StronglyTypedSortedBag
                .treeBag();
        statistics.accept(value -> bag.add(value));
        final FrequencyData frequencyData = new FrequencyData() {

            @Override
            public long max() {
                return statistics.max();
            }

            @Override
            public boolean hasSamples() {
                return statistics.hasSamples();
            }

            @Override
            public long countFor(long value) {
                return bag.count((int) value);
            }
        };
        writeGraph(graphTitle, xAxisTitle, graphName, frequencyData);
    }

    public void createFrequencyGraphLongValues(String graphTitle,
            String xAxisTitle, String graphName,
            StatisticsListProvider<Long> statistics) {
        final StronglyTypedSortedBag<Long> bag = StronglyTypedSortedBag
                .treeBag();
        statistics.accept(value -> bag.add(value));
        final FrequencyData frequencyData = new FrequencyData() {

            @Override
            public long max() {
                return statistics.max();
            }

            @Override
            public boolean hasSamples() {
                return statistics.hasSamples();
            }

            @Override
            public long countFor(long value) {
                return bag.count(value);
            }
        };
        writeGraph(graphTitle, xAxisTitle, graphName, frequencyData);
    }

    private void writeGraph(String graphTitle, String xAxisTitle,
            String graphName, final FrequencyData frequencyData) {
        FrequencyGraphBuilder.newFrequencyGraph(frequencyData, graphTitle,
                xAxisTitle, ADAPTER_FACTORY)
                .writeGraph(imageFactory, graphName);
    }
}
