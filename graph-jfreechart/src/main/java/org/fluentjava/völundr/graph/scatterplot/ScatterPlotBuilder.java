package org.fluentjava.völundr.graph.scatterplot;

import org.fluentjava.völundr.graph.ImageData;
import org.fluentjava.völundr.graph.ImageFactory;
import org.fluentjava.völundr.graph.SampleGraph;
import org.fluentjava.völundr.graph.ScatterPlotAdapterProvider;

public class ScatterPlotBuilder {

    private final SampleGraph sampleGraph;

    public ScatterPlotBuilder(SampleGraph sampleGraph) {
        this.sampleGraph = sampleGraph;
    }

    public static ScatterPlotBuilder newScatterPlotBuilder(
            final ScatterPlotData scatterPlotData) {
        return new ScatterPlotBuilder(new SampleGraph() {

            @Override
            public ImageData imageData() {
                return scatterPlotData.imageData();
            }

            @Override
            public boolean hasSamples() {
                return scatterPlotData.hasSamples();
            }
        });
    }

    public void writeGraph(ImageFactory imageFactory, String graphName) {
        if (sampleGraph.hasSamples()) { // no need to create an empty graph
            imageFactory.createScatterPlot(graphName, sampleGraph.imageData());
        }
    }

    public static ScatterPlotData newScatterPlotData(final String graphTitle,
            final String xAxisTitle, final String yAxisTitle,
            String legendTitle,
            final ScatterPlotAdapterProvider<?, ?> scatterPlotAdapterProvider) {
        ImageData imageData = ImageData.noStatistics(graphTitle, xAxisTitle,
                scatterPlotAdapterProvider.forScatterPlot(legendTitle,
                        yAxisTitle));
        return new ScatterPlotData() {
            boolean hasSamples = false;

            @Override
            public ImageData imageData() {
                return imageData;
            }

            @Override
            public synchronized void report(Number x, Number y) {
                if (!hasSamples) {
                    hasSamples = true;
                }
                imageData().add(x, y);
            }

            @Override
            public boolean hasSamples() {
                return hasSamples;
            }
        };

    }
}
