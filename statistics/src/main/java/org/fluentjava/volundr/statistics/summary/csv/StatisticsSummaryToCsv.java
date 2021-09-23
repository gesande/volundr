package org.fluentjava.volundr.statistics.summary.csv;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

public final class StatisticsSummaryToCsv {

    @SuppressWarnings("PMD.AssignmentInOperand")
    public static CsvSummary convertToCsv(String summary) {
        // TODO: use try-with-resources block
        try (StringReader reader = new StringReader(summary)) {
            try {
                try (BufferedReader br = new BufferedReader(reader)) {
                    try {
                        StringBuilder cols = new StringBuilder();
                        StringBuilder rows = new StringBuilder();
                        String line;
                        while ((line = br.readLine()) != null) {
                            String[] values = line.split(":");
                            String field = values[0].replaceAll("\\s{2,}", " ")
                                    .trim().replaceAll(" ", "_");
                            String value = values[1].trim();
                            cols.append(field).append(',');
                            rows.append(value).append(',');
                        }
                        String string = cols.toString();
                        String string2 = rows.toString();
                        return new CsvSummary(
                                string.substring(0, string.length() - 1),
                                string2.substring(0, string2.length() - 1));
                    } catch (IOException e) {
                        throw new StatisticsSummaryToCsvException(e);
                    }
                }
            } catch (IOException e) {
                throw new StatisticsSummaryToCsvException(e);
            }
        }
    }

    private static final class StatisticsSummaryToCsvException
            extends RuntimeException {

        public StatisticsSummaryToCsvException(Throwable cause) {
            super(cause);
        }
    }

    public static final class CsvSummary {

        private final String columnRow;
        private final String valueRow;

        private CsvSummary(String columnRow, String valueRow) {
            this.columnRow = columnRow;
            this.valueRow = valueRow;
        }

        public String build() {
            return columnRow() + "\n" + valueRow() + "\n";
        }

        public String columnRow() {
            return this.columnRow;
        }

        public String valueRow() {
            return this.valueRow;
        }
    }
}
