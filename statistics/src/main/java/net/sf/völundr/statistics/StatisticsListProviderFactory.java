package net.sf.völundr.statistics;

import java.util.List;

public final class StatisticsListProviderFactory {
    public static StatisticsListProvider<Long> longValues(List<Long> values) {
        return new StatisticsListProvider<>(values, new LongNumberProvider());
    }

    public static StatisticsListProvider<Integer> integerValues(
            List<Integer> values) {
        return new StatisticsListProvider<>(values,
                new IntegerNumberProvider());
    }
}