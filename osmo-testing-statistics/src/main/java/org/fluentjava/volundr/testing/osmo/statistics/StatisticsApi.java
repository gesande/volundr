package org.fluentjava.volundr.testing.osmo.statistics;

import java.util.function.Consumer;

public interface StatisticsApi {
    StatisticsApi statsTitle(final String statsTitle);

    StatisticsApi scatterPlot(String scatterPlotGraphName,
            ScatterStatisticsReporter reporter);

    StatisticsApi consumedBy(final Consumer<String> statsConsumer);

    StatisticsApi frequencyGraph(final String graphTitle,
            final String xAxisTitle, final String graphName);

    void publish();

}
