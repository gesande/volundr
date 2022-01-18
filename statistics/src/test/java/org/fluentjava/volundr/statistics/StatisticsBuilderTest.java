package org.fluentjava.volundr.statistics;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

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
                .consumedBy(stats -> assertEquals("""
                        title
                        samples       : 100
                        max           : 99
                        min           : 0
                        mean          : 49.5
                        median        : 49
                        95 percentile : 95
                        std           : 28.86607004772212
                        variance      : 833.25
                        """, stats));
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
                .consumedBy(stats -> assertEquals("""
                        title
                        samples       : 100
                        max           : 99
                        min           : 0
                        mean          : 49.5
                        median        : 49
                        95 percentile : 95
                        std           : 28.86607004772212
                        variance      : 833.25
                        """, stats));
    }

    @Test
    public void noVariance() {
        List<Integer> values = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            values.add(i);
        }
        StatisticsBuilder.withIntegerValues(values).title("title").samples()
                .max().min().mean().median().percentile95().std()
                .consumedBy(stats -> assertEquals("""
                        title
                        samples       : 100
                        max           : 99
                        min           : 0
                        mean          : 49.5
                        median        : 49
                        95 percentile : 95
                        std           : 28.86607004772212
                        """, stats));
    }

    @Test
    public void noStd() {
        List<Integer> values = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            values.add(i);
        }
        StatisticsBuilder.withIntegerValues(values).title("title").samples()
                .max().min().mean().median().percentile95().variance()
                .consumedBy(stats -> assertEquals("""
                        title
                        samples       : 100
                        max           : 99
                        min           : 0
                        mean          : 49.5
                        median        : 49
                        95 percentile : 95
                        variance      : 833.25
                        """, stats));
    }

    @Test
    public void no95percentile() {
        List<Integer> values = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            values.add(i);
        }
        StatisticsBuilder.withIntegerValues(values).title("title").samples()
                .max().min().mean().median().std().variance()
                .consumedBy(stats -> assertEquals("""
                        title
                        samples       : 100
                        max           : 99
                        min           : 0
                        mean          : 49.5
                        median        : 49
                        std           : 28.86607004772212
                        variance      : 833.25
                        """, stats));
    }

    @Test
    public void noMedian() {
        List<Integer> values = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            values.add(i);
        }
        StatisticsBuilder.withIntegerValues(values).title("title").samples()
                .max().min().mean().percentile95().std().variance()
                .consumedBy(stats -> assertEquals("""
                        title
                        samples       : 100
                        max           : 99
                        min           : 0
                        mean          : 49.5
                        95 percentile : 95
                        std           : 28.86607004772212
                        variance      : 833.25
                        """, stats));
    }

    @Test
    public void noMean() {
        List<Integer> values = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            values.add(i);
        }
        StatisticsBuilder.withIntegerValues(values).title("title").samples()
                .max().min().median().percentile95().std().variance()
                .consumedBy(stats -> assertEquals("""
                        title
                        samples       : 100
                        max           : 99
                        min           : 0
                        median        : 49
                        95 percentile : 95
                        std           : 28.86607004772212
                        variance      : 833.25
                        """, stats));
    }

    @Test
    public void noMin() {
        List<Integer> values = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            values.add(i);
        }
        StatisticsBuilder.withIntegerValues(values).title("title").samples()
                .max().mean().median().percentile95().std().variance()
                .consumedBy(stats -> assertEquals("""
                        title
                        samples       : 100
                        max           : 99
                        mean          : 49.5
                        median        : 49
                        95 percentile : 95
                        std           : 28.86607004772212
                        variance      : 833.25
                        """, stats));
    }

    @Test
    public void noMax() {
        List<Integer> values = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            values.add(i);
        }
        StatisticsBuilder.withIntegerValues(values).title("title").samples()
                .min().mean().median().percentile95().std().variance()
                .consumedBy(stats -> assertEquals("""
                        title
                        samples       : 100
                        min           : 0
                        mean          : 49.5
                        median        : 49
                        95 percentile : 95
                        std           : 28.86607004772212
                        variance      : 833.25
                        """, stats));
    }

    @Test
    public void noSamples() {
        List<Integer> values = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            values.add(i);
        }
        StatisticsBuilder.withIntegerValues(values).title("title").max().min()
                .mean().median().percentile95().std().variance()
                .consumedBy(stats -> assertEquals("""
                        title
                        max           : 99
                        min           : 0
                        mean          : 49.5
                        median        : 49
                        95 percentile : 95
                        std           : 28.86607004772212
                        variance      : 833.25
                        """, stats));
    }

}
