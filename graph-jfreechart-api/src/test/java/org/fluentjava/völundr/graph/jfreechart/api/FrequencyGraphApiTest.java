package org.fluentjava.völundr.graph.jfreechart.api;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.fluentjava.völundr.statistics.StatisticsListProvider;
import org.fluentjava.völundr.statistics.StatisticsListProviderFactory;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class FrequencyGraphApiTest {
    private static byte[] goldenMasterBytes;
    private static String targetPath;

    @BeforeClass
    public static void loadGoldenMaster() throws IOException {
        File goldenMaster = new File(FrequencyGraphApiTest.class
                .getClassLoader().getResource("goldenMaster.png").getFile());
        goldenMasterBytes = Files
                .readAllBytes(Paths.get(goldenMaster.getPath()));
        targetPath = System.getProperty("user.dir") + "/target";
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

        String graphName = "writeGraphFromStatisticsListProviderIntValues";
        new FrequencyGraphApi(targetPath).createFrequencyGraph("graphTitle",
                "xAxisTitle", graphName, stats);
        byte[] bytes = Files
                .readAllBytes(Paths.get(targetPath, graphName + ".png"));
        Assert.assertArrayEquals(goldenMasterBytes, bytes);
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

        String graphName = "writeGraphFromStatisticsListProviderLongValues";
        new FrequencyGraphApi(targetPath).createFrequencyGraphLongValues(
                "graphTitle", "xAxisTitle", graphName, stats);
        byte[] bytes = Files
                .readAllBytes(Paths.get(targetPath, graphName + ".png"));
        Assert.assertArrayEquals(goldenMasterBytes, bytes);
    }

}
