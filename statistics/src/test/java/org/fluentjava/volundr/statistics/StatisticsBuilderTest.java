package org.fluentjava.volundr.statistics;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

@SuppressWarnings("PMD.AddEmptyString")
public class StatisticsBuilderTest {

    @Test
    public void allFields() {
        List<Integer> values = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            values.add(i);
        }
        StatisticsBuilder.withIntegerValues(values).title("title").samples()
                .max().min().mean().median().percentile95().std().variance()
                .consumedBy(stats -> assertEquals("title\n"
                        + "samples       : 100\n" + "max           : 99\n"
                        + "min           : 0\n" + "mean          : 49.5\n"
                        + "median        : 49\n" + "95 percentile : 95\n"
                        + "std           : 28.86607004772212\n"
                        + "variance      : 833.25\n" + "", stats));
    }

    @Test
    public void withProvider() {
        List<Integer> values = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            values.add(i);
        }
        StatisticsListProvider<Integer> provider = StatisticsListProviderFactory
                .integerValues(values);
        StatisticsBuilder.with(provider).title("title").samples().max().min()
                .mean().median().percentile95().std().variance()
                .consumedBy(stats -> assertEquals("title\n"
                        + "samples       : 100\n" + "max           : 99\n"
                        + "min           : 0\n" + "mean          : 49.5\n"
                        + "median        : 49\n" + "95 percentile : 95\n"
                        + "std           : 28.86607004772212\n"
                        + "variance      : 833.25\n" + "", stats));
    }

    @Test
    public void noVariance() {
        List<Integer> values = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            values.add(i);
        }
        StatisticsBuilder.withIntegerValues(values).title("title").samples()
                .max().min().mean().median().percentile95().std()
                .consumedBy(stats -> assertEquals("title\n"
                        + "samples       : 100\n" + "max           : 99\n"
                        + "min           : 0\n" + "mean          : 49.5\n"
                        + "median        : 49\n" + "95 percentile : 95\n"
                        + "std           : 28.86607004772212\n" + "", stats));
    }

    @Test
    public void noStd() {
        List<Integer> values = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            values.add(i);
        }
        StatisticsBuilder.withIntegerValues(values).title("title").samples()
                .max().min().mean().median().percentile95().variance()
                .consumedBy(stats -> assertEquals("title\n"
                        + "samples       : 100\n" + "max           : 99\n"
                        + "min           : 0\n" + "mean          : 49.5\n"
                        + "median        : 49\n" + "95 percentile : 95\n"
                        + "variance      : 833.25\n" + "", stats));
    }

    @Test
    public void no95percentile() {
        List<Integer> values = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            values.add(i);
        }
        StatisticsBuilder.withIntegerValues(values).title("title").samples()
                .max().min().mean().median().std().variance()
                .consumedBy(stats -> assertEquals("title\n"
                        + "samples       : 100\n" + "max           : 99\n"
                        + "min           : 0\n" + "mean          : 49.5\n"
                        + "median        : 49\n"
                        + "std           : 28.86607004772212\n"
                        + "variance      : 833.25\n" + "", stats));
    }

    @Test
    public void noMedian() {
        List<Integer> values = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            values.add(i);
        }
        StatisticsBuilder.withIntegerValues(values).title("title").samples()
                .max().min().mean().percentile95().std().variance()
                .consumedBy(stats -> assertEquals("title\n"
                        + "samples       : 100\n" + "max           : 99\n"
                        + "min           : 0\n" + "mean          : 49.5\n"
                        + "95 percentile : 95\n"
                        + "std           : 28.86607004772212\n"
                        + "variance      : 833.25\n" + "", stats));
    }

    @Test
    public void noMean() {
        List<Integer> values = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            values.add(i);
        }
        StatisticsBuilder.withIntegerValues(values).title("title").samples()
                .max().min().median().percentile95().std().variance()
                .consumedBy(stats -> assertEquals("title\n"
                        + "samples       : 100\n" + "max           : 99\n"
                        + "min           : 0\n" + "median        : 49\n"
                        + "95 percentile : 95\n"
                        + "std           : 28.86607004772212\n"
                        + "variance      : 833.25\n" + "", stats));
    }

    @Test
    public void noMin() {
        List<Integer> values = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            values.add(i);
        }
        StatisticsBuilder.withIntegerValues(values).title("title").samples()
                .max().mean().median().percentile95().std().variance()
                .consumedBy(stats -> assertEquals("title\n"
                        + "samples       : 100\n" + "max           : 99\n"
                        + "mean          : 49.5\n" + "median        : 49\n"
                        + "95 percentile : 95\n"
                        + "std           : 28.86607004772212\n"
                        + "variance      : 833.25\n" + "", stats));
    }

    @Test
    public void noMax() {
        List<Integer> values = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            values.add(i);
        }
        StatisticsBuilder.withIntegerValues(values).title("title").samples()
                .min().mean().median().percentile95().std().variance()
                .consumedBy(stats -> assertEquals("title\n"
                        + "samples       : 100\n" + "min           : 0\n"
                        + "mean          : 49.5\n" + "median        : 49\n"
                        + "95 percentile : 95\n"
                        + "std           : 28.86607004772212\n"
                        + "variance      : 833.25\n" + "", stats));
    }

    @Test
    public void noSamples() {
        List<Integer> values = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            values.add(i);
        }
        StatisticsBuilder.withIntegerValues(values).title("title").max().min()
                .mean().median().percentile95().std().variance()
                .consumedBy(stats -> assertEquals("title\n"
                        + "max           : 99\n" + "min           : 0\n"
                        + "mean          : 49.5\n" + "median        : 49\n"
                        + "95 percentile : 95\n"
                        + "std           : 28.86607004772212\n"
                        + "variance      : 833.25\n" + "", stats));
    }

}
