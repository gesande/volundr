package org.fluentjava.völundr.testing.osmo.statistics;

import java.util.List;

public interface CsvSummaryConsumer {

    void consume(String graphName, String csvSummary);

    void consume(String graphName, List<String> values);

}
