package net.sf.völundr.statistics;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public final class StatisticsBuilder {
    enum Field {
        title, samples, max, min, mean, median, percentile95, std, variance
    }

    private final StatisticsListProvider<?> provider;
    private final Map<Field, BiConsumer<StringBuilder, StatisticsListProvider<?>>> statsBuilder = new TreeMap<>();

    private StatisticsBuilder(StatisticsListProvider<?> provider) {
        this.provider = provider;
    }

    public StatisticsBuilder title(String title) {
        add(Field.title, (sb, provider) -> sb.append(title).append("\n"));
        return this;
    }

    public StatisticsBuilder samples() {
        add(Field.samples, (sb, provider) -> sb.append("samples       ")
                .append(": ").append(provider.samples()).append("\n"));
        return this;
    }

    public StatisticsBuilder max() {
        add(Field.max, (sb, provider) -> sb.append("max           ")
                .append(": ").append(provider.max()).append("\n"));
        return this;
    }

    public StatisticsBuilder min() {
        add(Field.min, (sb, provider) -> sb.append("min           ")
                .append(": ").append(provider.min()).append("\n"));
        return this;
    }

    public StatisticsBuilder mean() {
        add(Field.mean, (sb, provider) -> sb.append("mean          ")
                .append(": ").append(provider.mean()).append("\n"));
        return this;
    }

    public StatisticsBuilder median() {
        add(Field.median, (sb, provider) -> sb.append("median        ")
                .append(": ").append(provider.median()).append("\n"));
        return this;
    }

    public StatisticsBuilder percentile95() {
        add(Field.percentile95, (sb, provider) -> sb.append("95 percentile ")
                .append(": ").append(provider.percentile(95)).append("\n"));
        return this;
    }

    public StatisticsBuilder std() {
        add(Field.std,
                (sb, provider) -> sb.append("std           ").append(": ")
                        .append(provider.standardDeviation()).append("\n"));
        return this;
    }

    public StatisticsBuilder variance() {
        add(Field.variance, (sb, provider) -> sb.append("variance      ")
                .append(": ").append(provider.variance()).append("\n"));
        return this;
    }

    private void add(Field field,
            BiConsumer<StringBuilder, StatisticsListProvider<?>> action) {
        if (!statsBuilder.containsKey(field)) {
            statsBuilder.put(field, action);
        }
    }

    public void consumedBy(Consumer<String> statsConsumer) {
        StringBuilder sb = new StringBuilder();
        statsBuilder.forEach((field, action) -> action.accept(sb, provider));
        statsConsumer.accept(sb.toString());
    }

    public static StatisticsBuilder withLongValues(List<Long> values) {
        return new StatisticsBuilder(
                StatisticsListProviderFactory.longValues(values));
    }

    public static StatisticsBuilder withIntegerValues(List<Integer> values) {
        return new StatisticsBuilder(
                StatisticsListProviderFactory.integerValues(values));
    }
}
