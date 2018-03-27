package org.fluentjava.völundr.graph.jfreechart.api;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.fluentjava.völundr.bag.StronglyTypedSortedBag;
import org.fluentjava.völundr.statistics.StatisticsListProvider;
import org.fluentjava.völundr.statistics.StatisticsListProviderFactory;
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

    private static byte[] goldenMasterBytesOf(String goldenMasterNamePrefix)
            throws IOException {
        File goldenMaster = new File(ChartGraphApiTest.class.getClassLoader()
                .getResource(goldenMasterNamePrefix + "-goldenMaster.png")
                .getFile());
        return Files.readAllBytes(Paths.get(goldenMaster.getPath()));
    }

}
