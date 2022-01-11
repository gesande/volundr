package org.fluentjava.volundr.testing.osmo.statistics;

import static java.nio.file.Files.createDirectories;
import static java.nio.file.Files.write;
import static java.nio.file.Files.writeString;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import org.fluentjava.volundr.graph.jfreechart.api.ChartGraphApi;
import org.fluentjava.volundr.graph.scatterplot.ScatterPlotData;
import org.fluentjava.volundr.statistics.StatisticsBuilder;
import org.fluentjava.volundr.statistics.StatisticsListProvider;
import org.fluentjava.volundr.statistics.StatisticsListProviderFactory;
import org.fluentjava.volundr.statistics.summary.csv.StatisticsSummaryToCsv;

import lombok.extern.slf4j.Slf4j;

@Slf4j
final class CsvFileSummaryConsumer implements CsvSummaryConsumer {
    private final String targetPath;

    public CsvFileSummaryConsumer(String targetPath) {
        this.targetPath = targetPath;
    }

    private Path csvFile(String fileName) throws IOException {
        log.debug("csvFile {}", fileName);
        createDirectories(Paths.get(targetPath));
        return Paths.get(targetPath, fileName + ".csv");
    }

    @Override
    public void consume(String graphName, String csvSummary) {
        log.debug("Write csv summary: {}", graphName);
        try {
            writeString(csvFile(graphName), csvSummary);
        } catch (Exception e) {
            throw new StatisticsApiRuntimeException(e);
        }
    }

    @Override
    public void consume(String graphName, List<String> values) {
        log.debug("Write values: {}, {} records", graphName, values.size());
        try {
            write(csvFile(graphName), values);
        } catch (Exception e) {
            throw new StatisticsApiRuntimeException(e);
        }
    }

}

final class StatisticsApiRuntimeException extends RuntimeException {

    public StatisticsApiRuntimeException(Throwable cause) {
        super(cause);
    }

}

public final class StatisticsApiBuilderImpl implements StatisticsApiBuilder {
    private final ChartGraphApi chartGraphApi;
    private final CsvSummaryConsumer csvSummaryConsumer;

    public StatisticsApiBuilderImpl(ChartGraphApi chartGraphApi,
            CsvSummaryConsumer csvSummaryConsumer) {
        this.chartGraphApi = chartGraphApi;
        this.csvSummaryConsumer = csvSummaryConsumer;
    }

    public static StatisticsApiBuilder withTargetPath(String targetPath) {
        return new StatisticsApiBuilderImpl(new ChartGraphApi(targetPath),
                new CsvFileSummaryConsumer(targetPath));
    }

    @Override
    public ScatterStatisticsReporter scatterStatisticsReporter(
            String graphTitle, String xAxisTitle, String yAxisTitle,
            String legendTitle) {
        return new ScatterStatisticsReporterImpl(
                this.chartGraphApi.createScatterPlotData(graphTitle, xAxisTitle,
                        yAxisTitle, legendTitle));
    }

    protected static class Tuple<X, Y> {
        private final X x;
        private final Y y;

        public Tuple(X x, Y y) {
            this.x = x;
            this.y = y;
        }
    }

    static class ScatterStatisticsReporterImpl
            implements ScatterStatisticsReporter {
        private final ScatterPlotData scatterPlotData;
        private final List<Tuple<Number, Number>> values = new ArrayList<>();

        public ScatterStatisticsReporterImpl(ScatterPlotData scatterPlotData) {
            this.scatterPlotData = scatterPlotData;
        }

        @Override
        public void report(Number x, Number y) {
            values.add(new Tuple<>(x, y));
            scatterPlotData.report(x, y);
        }

    }

    static class StatisticsApiImpl implements StatisticsApi {
        private final List<Long> latencies;
        private final ChartGraphApi chartGraphApi;
        private final CsvSummaryConsumer csvSummaryConsumer;

        private String statsTitle;
        private Consumer<String> statsConsumer;
        private String graphTitle;
        private String xAxisTitle;
        private String graphName;
        private boolean graphOptionsPassed;
        private String scatterPlotGraphName;
        private ScatterStatisticsReporter scatterReporter;

        public StatisticsApiImpl(List<Long> latencies,
                ChartGraphApi chartGraphApi,
                CsvSummaryConsumer csvSummaryConsumer) {
            this.latencies = latencies;
            this.chartGraphApi = chartGraphApi;
            this.csvSummaryConsumer = csvSummaryConsumer;
        }

        @Override
        public StatisticsApi statsTitle(String statsTitle) {
            this.statsTitle = statsTitle;
            return this;
        }

        @Override
        public StatisticsApi consumedBy(Consumer<String> statsConsumer) {
            this.statsConsumer = statsConsumer;
            return this;
        }

        @Override
        public StatisticsApi frequencyGraph(String graphTitle,
                String xAxisTitle, String graphName) {
            this.graphOptionsPassed = true;
            this.graphTitle = graphTitle;
            this.xAxisTitle = xAxisTitle;
            this.graphName = graphName;
            return this;
        }

        @Override
        public StatisticsApi scatterPlot(String scatterPlotGraphName,
                ScatterStatisticsReporter reporter) {
            this.scatterPlotGraphName = scatterPlotGraphName;
            this.scatterReporter = reporter;
            return this;
        }

        @Override
        public void publish() {
            StatisticsListProvider<Long> statistics = StatisticsListProviderFactory
                    .longValues(latencies);

            StatisticsBuilder.with(statistics).title(statsTitle)

                    .samples().max().min().mean().median().percentile95().std()
                    .variance()

                    .consumedBy(summary -> {
                        statsConsumer.accept(summary);
                        consumeCsvSummary(summary);
                    });

            consumeLatencyCsvData();
            if (graphOptionsPassed) {
                chartGraphApi.createFrequencyGraphLongValues(graphTitle,
                        xAxisTitle, graphName, statistics);
            }
            if (scatterPlotGraphName != null
                    && scatterReporter instanceof ScatterStatisticsReporterImpl scatterStatisticsReporterImpl) {
                // hackish, but don't want to spread völundr data types around
                chartGraphApi.createScatterPlot(
                        scatterStatisticsReporterImpl.scatterPlotData,
                        scatterPlotGraphName);
                consumerScatterCsvData(scatterPlotGraphName,
                        scatterStatisticsReporterImpl.values);
            }
        }

        private void consumeCsvSummary(String summary) {
            String summaryWithoutTitle = summary
                    .substring(summary.indexOf('\n') + 1);
            String csvSummary = StatisticsSummaryToCsv
                    .convertToCsv(summaryWithoutTitle).build();
            csvSummaryConsumer.consume(graphName + ".summary", csvSummary);
        }

        private void consumerScatterCsvData(String graphName,
                List<Tuple<Number, Number>> tuples) {
            List<String> values = tuples.stream()
                    .map(tuple -> tuple.x.toString() + "," + tuple.y.toString())
                    .collect(Collectors.toList());
            values.add(0, "Devices, Latency");
            csvSummaryConsumer.consume(graphName, values);
        }

        private void consumeLatencyCsvData() {
            List<String> values = latencies.stream().map(Object::toString)
                    .collect(Collectors.toList());
            values.add(0, "Latency");
            csvSummaryConsumer.consume(graphName, values);
        }
    }

    @Override
    public StatisticsApi withValues(List<Long> latencies) {
        return new StatisticsApiImpl(latencies, chartGraphApi,
                csvSummaryConsumer);
    }
}
