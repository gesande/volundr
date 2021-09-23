package org.fluentjava.volundr.testing.osmo.statistics;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public final class StatisticsConsumer implements Consumer<String> {
    private final List<String> stats = new ArrayList<>();

    @Override
    public void accept(String t) {
        stats.add("\n" + t);
    }

    public String stats() {
        StringBuilder result = new StringBuilder();
        stats.forEach(result::append);
        return result.toString();
    }
}
