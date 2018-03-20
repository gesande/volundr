package org.fluentjava.völundr.graph.frequency;

import org.fluentjava.völundr.graph.ImageData;
import org.fluentjava.völundr.graph.ImageFactory;
import org.fluentjava.völundr.graph.LineChartAdapterProvider;
import org.fluentjava.völundr.graph.SampleGraph;

public final class FrequencyGraphBuilder {

    private static final String LEGEND_TITLE = "Frequency";
    private SampleGraph sampleGraph;

    private FrequencyGraphBuilder(SampleGraph sampleGraph) {
        this.sampleGraph = sampleGraph;
    }

    public static FrequencyGraphBuilder newFrequencyGraph(
            final FrequencyData frequencyData, final String graphTitle,
            final String xAxisTitle,
            final LineChartAdapterProvider<?, ?> lineChartAdapterProvider) {
        return new FrequencyGraphBuilder(new SampleGraph() {

            @Override
            public ImageData imageData() {
                final ImageData imageData = ImageData.noStatistics(graphTitle,
                        xAxisTitle,
                        lineChartAdapterProvider.forLineChart(LEGEND_TITLE));
                final long maxLatency = frequencyData.max();
                long range = 0;
                for (long i = 0; i <= maxLatency; i++) {
                    long count = frequencyData.countFor(i);
                    if (count > range) {
                        range = count;
                    }
                    imageData.add(i, count);
                }
                return imageData.range(range + 5.0);
            }

            @Override
            public boolean hasSamples() {
                return frequencyData.hasSamples();
            }
        });
    }

    public void writeGraph(ImageFactory imageFactory, String graphName) {
        if (sampleGraph.hasSamples()) { // no need to create an empty graph
            imageFactory.createXYLineChart(graphName, sampleGraph.imageData());
        }
    }
}
