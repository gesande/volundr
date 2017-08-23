package net.sf.völundr.statistics;

import java.util.List;

public final class StatisticsListProviderFactory {
    public static StatisticsListProvider<Long> longValues(
            final List<Long> values) {
        return StatisticsListProvider.fromValues(values,
                new LongNumberProvider());
    }

    public static StatisticsListProvider<Integer> integerValues(
            final List<Integer> values) {
        return StatisticsListProvider.fromValues(values,
                new IntegerNumberProvider());
    }
}