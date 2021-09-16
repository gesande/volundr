package org.fluentjava.volundr.graph.scatterplot;

import org.fluentjava.volundr.graph.ImageData;
import org.fluentjava.volundr.graph.ImageFactory;
import org.fluentjava.volundr.graph.SampleGraph;
import org.fluentjava.volundr.graph.ScatterPlotAdapterProvider;

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
            boolean hasSamples;

            @Override
            public ImageData imageData() {
                return imageData;
            }

            @Override
            public void report(Number x, Number y) {
                synchronized (this) {
                    if (!hasSamples) {
                        hasSamples = true;
                    }
                    imageData().add(x, y);
                }
            }

            @Override
            public boolean hasSamples() {
                return hasSamples;
            }
        };

    }
}
