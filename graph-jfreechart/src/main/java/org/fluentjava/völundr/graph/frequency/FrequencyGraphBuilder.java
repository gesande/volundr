package org.fluentjava.völundr.graph.frequency;

import org.fluentjava.völundr.graph.ImageData;
import org.fluentjava.völundr.graph.LineChartAdapterProvider;
import org.fluentjava.völundr.graph.SampleGraph;

public final class FrequencyGraphBuilder {

    private static final String LEGEND_TITLE = "Frequency";

    private FrequencyGraphBuilder() {
    }

    public static SampleGraph newFrequencyGraph(
            final FrequencyData frequencyData, final String graphTitle,
            final String xAxisTitle,
            final LineChartAdapterProvider<?, ?> lineChartAdapterProvider) {
        return new SampleGraph() {

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
        };
    }
}
