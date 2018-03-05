package net.sf.völundr.statistics;

import java.util.List;
import java.util.function.Consumer;

public final class DefaultStatisticsConsumer {

    private final List<Long> latencies;
    private final String title;

    public DefaultStatisticsConsumer(String title, List<Long> latencies) {
        this.title = title;
        this.latencies = latencies;
    }

    public static DefaultStatisticsConsumer statsConsumer(String title,
            List<Long> latencies) {
        return new DefaultStatisticsConsumer(title, latencies);
    }

    public void consumeStatistics(Consumer<String> consumer) {
        StatisticsListProvider<Long> calc = StatisticsListProviderFactory
                .longValues(latencies);
        StringBuilder sb = new StringBuilder();

        sb.append(title).append("\n");
        sb.append("samples       ").append(": ").append(latencies.size())
                .append("\n");
        sb.append("max           ").append(": ").append(calc.max())
                .append("\n");
        sb.append("min           ").append(": ").append(calc.min())
                .append("\n");
        sb.append("mean          ").append(": ").append(calc.mean())
                .append("\n");
        sb.append("median        ").append(": ").append(calc.median())
                .append("\n");
        sb.append("95 percentile ").append(": ").append(calc.percentile(95))
                .append("\n");
        sb.append("std           ").append(": ")
                .append(calc.standardDeviation()).append("\n");
        sb.append("variance      ").append(": ").append(calc.variance())
                .append("\n");

        consumer.accept(sb.toString());

    }
}
