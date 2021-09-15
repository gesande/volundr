package org.fluentjava.volundr.statistics.summary.csv;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class StatisticsSummaryToCsvTest {

    @Test
    public void summaryToCsv() {
        String summary = "samples       : 5\n" + "max           : 1302\n"
                + "min           : 1113\n" + "mean          : 1166.0\n"
                + "median        : 1140\n" + "95 percentile : 1302\n"
                + "std           : 69.23871749245504\n"
                + "variance      : 4793.999999999999\n";
        String result = "samples,max,min,mean,median,95_percentile,std,variance\n"
                + "5,1302,1113,1166.0,1140,1302,69.23871749245504,4793.999999999999\n";

        assertEquals(result,
                StatisticsSummaryToCsv.convertToCsv(summary).build());
    }
}
