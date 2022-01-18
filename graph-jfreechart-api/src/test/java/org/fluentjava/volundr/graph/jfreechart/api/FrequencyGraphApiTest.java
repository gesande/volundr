package org.fluentjava.volundr.graph.jfreechart.api;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.fluentjava.volundr.statistics.StatisticsListProvider;
import org.fluentjava.volundr.statistics.StatisticsListProviderFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

@SuppressWarnings("PMD.UseProperClassLoader")
public class FrequencyGraphApiTest {
    private static byte[] goldenMasterBytes;
    private static String targetPath;

    @BeforeAll
    public static void loadGoldenMaster() throws IOException {
        File goldenMaster = new File(requireNonNull(FrequencyGraphApiTest.class
                .getResource("/frequency-goldenMaster.png")).getFile());
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

        String graphName = "FrequencyGraphApiTest-writeGraphFromStatisticsListProviderIntValues";
        new FrequencyGraphApi(targetPath).createFrequencyGraph("graphTitle",
                "xAxisTitle", graphName, stats);
        byte[] bytes = Files
                .readAllBytes(Paths.get(targetPath, graphName + ".png"));
        assertArrayEquals(goldenMasterBytes, bytes);
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

        String graphName = "FrequencyGraphApiTest-writeGraphFromStatisticsListProviderLongValues";
        new FrequencyGraphApi(targetPath).createFrequencyGraphLongValues(
                "graphTitle", "xAxisTitle", graphName, stats);
        byte[] bytes = Files
                .readAllBytes(Paths.get(targetPath, graphName + ".png"));
        assertArrayEquals(goldenMasterBytes, bytes);
    }

}
