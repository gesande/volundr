package org.fluentjava.völundr.graph;

public final class ImageDataFactory {
    private final LineChartAdapterProvider<?, ?> lineChartAdapterProvider;

    public ImageDataFactory(
            final LineChartAdapterProvider<?, ?> lineChartAdapterProvider) {
        this.lineChartAdapterProvider = lineChartAdapterProvider;
    }

    public ImageData newImageDataForLineChart(final GraphOptions graphOptions,
            final GraphStatisticsProvider statistics) {
        return newImageData(linechartAdapter(graphOptions.legendTitle()),
                graphOptions, statistics);
    }

    public ImageData newImageDataForLineChart(final String title,
            final String xAxisTitle) {
        return ImageData.noStatistics(title, xAxisTitle,
                linechartAdapter(title));
    }

    private DatasetAdapter<?, ?> linechartAdapter(final String legendTitle) {
        return lineChartAdapterProvider().forLineChart(legendTitle);
    }

    private static ImageData newImageData(final DatasetAdapter<?, ?> adapter,
            final GraphOptions graphOptions,
            final GraphStatisticsProvider statistics) {
        return newImageData(adapter, graphOptions, statistics,
                graphOptions.title(), graphOptions.xAxisTitle(),
                graphOptions.range());
    }

    private static ImageData newImageData(final DatasetAdapter<?, ?> adapter,
            final GraphOptions graphOptions,
            final GraphStatisticsProvider statistics, final String title,
            final String xAxisTitle, final int range) {
        return graphOptions.provideStatistics()
                ? ImageData.statistics(title, xAxisTitle, range, statistics,
                        adapter)
                : ImageData.noStatistics(title, xAxisTitle, range, adapter);
    }

    private LineChartAdapterProvider<?, ?> lineChartAdapterProvider() {
        return this.lineChartAdapterProvider;
    }
}
