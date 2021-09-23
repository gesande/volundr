package org.fluentjava.volundr.statistics;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public final class DefaultStatisticsConsumer {

    private final List<Long> latencies = new ArrayList<>();
    private final String title;

    public DefaultStatisticsConsumer(String title, List<Long> latencies) {
        this.title = title;
        this.latencies.addAll(latencies);
    }

    public static DefaultStatisticsConsumer statsConsumer(String title,
            List<Long> latencies) {
        return new DefaultStatisticsConsumer(title, latencies);
    }

    public void consumeStatistics(Consumer<String> consumer) {
        StatisticsBuilder.withLongValues(latencies).title(title).samples().max()
                .min().mean().median().percentile95().std().variance()
                .consumedBy(consumer);
    }
}
