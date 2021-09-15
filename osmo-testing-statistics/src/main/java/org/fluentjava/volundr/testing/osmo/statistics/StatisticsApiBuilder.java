package org.fluentjava.volundr.testing.osmo.statistics;

import java.util.List;

public interface StatisticsApiBuilder {

    ScatterStatisticsReporter scatterStatisticsReporter(String graphTitle,
            String xAxisTitle, String yAxisTitle, String legendTitle);

    StatisticsApi withValues(final List<Long> latencies);

}
