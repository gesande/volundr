package org.fluentjava.völundr.graph.jfreechart;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.fluentjava.völundr.graph.ImageData;
import org.fluentjava.völundr.graph.SampleGraph;
import org.fluentjava.völundr.graph.frequency.FrequencyData;
import org.fluentjava.völundr.graph.frequency.FrequencyGraphBuilder;
import org.fluentjava.völundr.statistics.AbstractStatisticsValueProvider;
import org.fluentjava.völundr.statistics.StatisticsListProvider;
import org.fluentjava.völundr.statistics.StatisticsListProviderFactory;
import org.fluentjava.völundr.statistics.StatisticsValueProviderFactory;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class FrequencyGraphTest {

    private static byte[] goldenMasterBytes;

    @BeforeClass
    public static void loadGoldenMaster() throws IOException {
        File goldenMaster = new File(FrequencyGraphTest.class.getClassLoader()
                .getResource("goldenMaster.png").getFile());
        goldenMasterBytes = Files
                .readAllBytes(Paths.get(goldenMaster.getPath()));
    }

    @Test
    public void writeGraph() throws IOException {
        FrequencyData frequencyData = new FrequencyData() {

            @Override
            public long max() {
                return 8;
            }

            @Override
            public boolean hasSamples() {
                return true;
            }

            @SuppressWarnings("incomplete-switch")
            @Override
            public long countFor(long value) {
                switch ((int) value) {
                case 0:
                    return 0;
                case 1:
                    return 0;
                case 2:
                    return 1;
                case 3:
                    return 1;
                case 4:
                    return 2;
                case 5:
                    return 3;
                case 6:
                    return 2;
                case 7:
                    return 1;
                case 8:
                    return 1;
                }
                return 0;
            }
        };
        DefaultDatasetAdapterFactory lineChartAdapterProvider = new DefaultDatasetAdapterFactory();
        SampleGraph frequencyGraph = FrequencyGraphBuilder.newFrequencyGraph(
                frequencyData, "graphTitle", "xAxisTitle",
                lineChartAdapterProvider);
        ImageData imageData = frequencyGraph.imageData();
        String path = System.getProperty("user.dir") + "/target";
        String graphName = "writeGraph";

        new ImageFactoryUsingJFreeChart(new JFreeChartWriter(path))
                .createXYLineChart(graphName, imageData);
        assertTrue(frequencyGraph.hasSamples());
        assertEquals("graphTitle", imageData.title());
        assertEquals("xAxisTitle", imageData.xAxisLabel());
        assertEquals(8.0, imageData.range(), 0);
        byte[] bytes = Files.readAllBytes(Paths.get(path, graphName + ".png"));
        Assert.assertArrayEquals(goldenMasterBytes, bytes);
    }

    @Test
    public void writeGraphFromStatisticsValueProvider() throws IOException {
        AbstractStatisticsValueProvider<Long> stats = StatisticsValueProviderFactory
                .longValues();
        stats.addSample(2L);
        stats.addSample(3L);
        stats.addSample(4L);
        stats.addSample(4L);
        stats.addSample(5L);
        stats.addSample(5L);
        stats.addSample(5L);
        stats.addSample(6L);
        stats.addSample(6L);
        stats.addSample(7L);
        stats.addSample(8L);

        DefaultDatasetAdapterFactory lineChartAdapterProvider = new DefaultDatasetAdapterFactory();
        FrequencyData frequencyData = new FrequencyData() {

            @Override
            public long max() {
                return stats.max();
            }

            @Override
            public boolean hasSamples() {
                return stats.hasSamples();
            }

            @Override
            public long countFor(long value) {
                return stats.countFor(value);
            }
        };
        SampleGraph frequencyGraph = FrequencyGraphBuilder.newFrequencyGraph(
                frequencyData, "graphTitle", "xAxisTitle",
                lineChartAdapterProvider);
        ImageData imageData = frequencyGraph.imageData();
        String path = System.getProperty("user.dir") + "/target";
        String graphName = "writeGraphFromStatisticsValueProvider";

        new ImageFactoryUsingJFreeChart(new JFreeChartWriter(path))
                .createXYLineChart(graphName, imageData);
        assertTrue(frequencyGraph.hasSamples());
        assertEquals("graphTitle", imageData.title());
        assertEquals("xAxisTitle", imageData.xAxisLabel());
        assertEquals(8.0, imageData.range(), 0);
        byte[] bytes = Files.readAllBytes(Paths.get(path, graphName + ".png"));
        Assert.assertArrayEquals(goldenMasterBytes, bytes);
    }

    @Test
    public void writeGraphFromStatisticsListProvider() throws IOException {
        List<Long> values = new ArrayList<>();
        values.add(2L);
        values.add(3L);
        values.add(4L);
        values.add(4L);
        values.add(5L);
        values.add(5L);
        values.add(5L);
        values.add(6L);
        values.add(6L);
        values.add(7L);
        values.add(8L);

        StatisticsListProvider<Long> stats = StatisticsListProviderFactory
                .longValues(values);

        DefaultDatasetAdapterFactory lineChartAdapterProvider = new DefaultDatasetAdapterFactory();
        FrequencyData frequencyData = new FrequencyData() {

            @Override
            public long max() {
                return stats.max();
            }

            @Override
            public boolean hasSamples() {
                return stats.hasSamples();
            }

            @Override
            public long countFor(long value) {
                AtomicLong count = new AtomicLong(0);
                values.forEach(val -> {
                    if (val == value) {
                        count.getAndIncrement();
                    }
                });
                return count.get();
            }
        };
        SampleGraph frequencyGraph = FrequencyGraphBuilder.newFrequencyGraph(
                frequencyData, "graphTitle", "xAxisTitle",
                lineChartAdapterProvider);
        ImageData imageData = frequencyGraph.imageData();
        String path = System.getProperty("user.dir") + "/target";
        String graphName = "writeGraphFromStatisticsListProvider";

        new ImageFactoryUsingJFreeChart(new JFreeChartWriter(path))
                .createXYLineChart(graphName, imageData);

        assertTrue(frequencyGraph.hasSamples());
        assertEquals("graphTitle", imageData.title());
        assertEquals("xAxisTitle", imageData.xAxisLabel());
        assertEquals(8.0, imageData.range(), 0);
        byte[] bytes = Files.readAllBytes(Paths.get(path, graphName + ".png"));
        Assert.assertArrayEquals(goldenMasterBytes, bytes);
    }

    @Test
    public void writeGraphFromStatisticsListProviderIntValues()
            throws IOException {
        List<Integer> values = new ArrayList<>();
        values.add(2);
        values.add(3);
        values.add(4);
        values.add(4);
        values.add(5);
        values.add(5);
        values.add(5);
        values.add(6);
        values.add(6);
        values.add(7);
        values.add(8);

        StatisticsListProvider<Integer> stats = StatisticsListProviderFactory
                .integerValues(values);

        DefaultDatasetAdapterFactory lineChartAdapterProvider = new DefaultDatasetAdapterFactory();
        FrequencyData frequencyData = new FrequencyData() {

            @Override
            public long max() {
                return stats.max();
            }

            @Override
            public boolean hasSamples() {
                return stats.hasSamples();
            }

            @Override
            public long countFor(long value) {
                AtomicLong count = new AtomicLong(0);
                values.forEach(val -> {
                    if (val == value) {
                        count.getAndIncrement();
                    }
                });
                return count.get();
            }
        };
        SampleGraph frequencyGraph = FrequencyGraphBuilder.newFrequencyGraph(
                frequencyData, "graphTitle", "xAxisTitle",
                lineChartAdapterProvider);
        ImageData imageData = frequencyGraph.imageData();
        String path = System.getProperty("user.dir") + "/target";
        String graphName = "writeGraphFromStatisticsListProviderIntValues";

        new ImageFactoryUsingJFreeChart(new JFreeChartWriter(path))
                .createXYLineChart(graphName, imageData);

        assertTrue(frequencyGraph.hasSamples());
        assertEquals("graphTitle", imageData.title());
        assertEquals("xAxisTitle", imageData.xAxisLabel());
        assertEquals(8.0, imageData.range(), 0);
        byte[] bytes = Files.readAllBytes(Paths.get(path, graphName + ".png"));
        Assert.assertArrayEquals(goldenMasterBytes, bytes);
    }

}
