package org.fluentjava.völundr.graph.jfreechart.api;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.fluentjava.völundr.bag.StronglyTypedSortedBag;
import org.fluentjava.völundr.graph.scatterplot.ScatterPlotData;
import org.fluentjava.völundr.statistics.AbstractStatisticsValueProvider;
import org.fluentjava.völundr.statistics.StatisticsListProvider;
import org.fluentjava.völundr.statistics.StatisticsListProviderFactory;
import org.fluentjava.völundr.statistics.StatisticsValueProviderFactory;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ChartGraphApiTest {
    private static String targetPath;

    @BeforeClass
    public static void loadGoldenMaster() {
        targetPath = System.getProperty("user.dir") + "/target";
    }

    @Test
    public void writeLineGraphFromStatisticsListProviderIntValues()
            throws IOException {
        List<Integer> values = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            values.add(i);
        }

        StatisticsListProvider<Integer> stats = StatisticsListProviderFactory
                .integerValues(values);

        String graphName = "ChartGraphApiTest-writeGraphFromStatisticsListProviderIntValues";
        new ChartGraphApi(targetPath).createLineChart("legendTitle",
                "graphTitle", "xAxisTitle", graphName, stats);
        byte[] bytes = Files
                .readAllBytes(Paths.get(targetPath, graphName + ".png"));
        Assert.assertArrayEquals(goldenMasterBytesOf("samples"), bytes);
    }

    @Test
    public void barGraphFromBagOfValues() throws IOException {
        StronglyTypedSortedBag<Integer> bag = StronglyTypedSortedBag.treeBag();
        for (int i = 0; i < 100; i++) {
            bag.add(200);
        }
        for (int i = 0; i < 10; i++) {
            bag.add(204);
        }
        for (int i = 0; i < 3; i++) {
            bag.add(403);
        }
        for (int i = 0; i < 5; i++) {
            bag.add(500);
        }

        String graphName = "ChartGraphApiTest-barGraphFromBagOfValues";
        new ChartGraphApi(targetPath).createBarChart("legendTitle",
                "graphTitle", "xAxisTitle", graphName, bag);
        byte[] bytes = Files
                .readAllBytes(Paths.get(targetPath, graphName + ".png"));
        Assert.assertArrayEquals(goldenMasterBytesOf("barGraphFromBagOfValues"),
                bytes);
    }

    @Test
    public void barGraphFromStatisticsValueProvider() throws IOException {
        AbstractStatisticsValueProvider<Integer> stats = StatisticsValueProviderFactory
                .integerValues();
        for (int i = 0; i < 100; i++) {
            stats.addSample(200);
        }
        for (int i = 0; i < 10; i++) {
            stats.addSample(204);
        }
        for (int i = 0; i < 3; i++) {
            stats.addSample(403);
        }
        for (int i = 0; i < 5; i++) {
            stats.addSample(500);
        }

        String graphName = "ChartGraphApiTest-barGraphFromStatisticsValueProvider";
        new ChartGraphApi(targetPath).createBarChart("legendTitle",
                "graphTitle", "xAxisTitle", graphName, stats);
        byte[] bytes = Files
                .readAllBytes(Paths.get(targetPath, graphName + ".png"));
        Assert.assertArrayEquals(goldenMasterBytesOf("barGraphFromBagOfValues"),
                bytes);
    }

    @Test
    public void scatterPlotTest() throws IOException {
        ChartGraphApi chartGraphApi = new ChartGraphApi(targetPath);
        ScatterPlotData scatterPlotData = chartGraphApi.createScatterPlotData(
                "graphTitle", "Temperature (Celsius) ", "Sales ($)",
                "legendTitle");

        scatterPlotData.report(14.2, 215);
        scatterPlotData.report(16.4, 325);
        scatterPlotData.report(11.9, 185);
        scatterPlotData.report(15.2, 332);
        scatterPlotData.report(18.5, 406);
        scatterPlotData.report(22.1, 522);
        scatterPlotData.report(19.4, 412);
        scatterPlotData.report(25.1, 614);
        scatterPlotData.report(23.4, 544);
        scatterPlotData.report(18.1, 421);
        scatterPlotData.report(22.6, 445);
        scatterPlotData.report(17.2, 408);

        String graphName = "ChartGraphApiTest-scatterPlotTest";
        chartGraphApi.createScatterPlot(scatterPlotData, graphName);
        byte[] bytes = Files
                .readAllBytes(Paths.get(targetPath, graphName + ".png"));
        Assert.assertArrayEquals(goldenMasterBytesOf("plot"), bytes);
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

        String graphName = "ChartGraphApiTest-writeGraphFromStatisticsListProviderIntValues";
        new ChartGraphApi(targetPath).createFrequencyGraph("graphTitle",
                "xAxisTitle", graphName, stats);
        byte[] bytes = Files
                .readAllBytes(Paths.get(targetPath, graphName + ".png"));
        Assert.assertArrayEquals(goldenMasterBytesOf("frequency"), bytes);
    }

    @Test
    public void writeGraphFromStatisticsListProviderLongValues()
            throws IOException {
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

        String graphName = "ChartGraphApiTest-writeGraphFromStatisticsListProviderLongValues";
        new ChartGraphApi(targetPath).createFrequencyGraphLongValues(
                "graphTitle", "xAxisTitle", graphName, stats);
        byte[] bytes = Files
                .readAllBytes(Paths.get(targetPath, graphName + ".png"));
        Assert.assertArrayEquals(goldenMasterBytesOf("frequency"), bytes);
    }

    private static byte[] goldenMasterBytesOf(String goldenMasterNamePrefix)
            throws IOException {
        File goldenMaster = new File(ChartGraphApiTest.class.getClassLoader()
                .getResource(goldenMasterNamePrefix + "-goldenMaster.png")
                .getFile());
        return Files.readAllBytes(Paths.get(goldenMaster.getPath()));
    }
}
