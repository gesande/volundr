package org.fluentjava.volundr.statistics.summary.csv;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class StatisticsSummaryToCsvTest {

    @Test
    public void summaryToCsv() {
        String summary = """
                samples       : 5
                max           : 1302
                min           : 1113
                mean          : 1166.0
                median        : 1140
                95 percentile : 1302
                std           : 69.23871749245504
                variance      : 4793.999999999999
                """;
        String result = """
                samples,max,min,mean,median,95_percentile,std,variance
                5,1302,1113,1166.0,1140,1302,69.23871749245504,4793.999999999999
                """;

        assertEquals(result,
                StatisticsSummaryToCsv.convertToCsv(summary).build());
    }
}
