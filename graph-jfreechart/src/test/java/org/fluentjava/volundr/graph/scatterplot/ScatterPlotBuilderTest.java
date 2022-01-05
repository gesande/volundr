package org.fluentjava.volundr.graph.scatterplot;

import org.fluentjava.volundr.graph.frequency.FrequencyGraphBuilderTest;
import org.fluentjava.volundr.graph.jfreechart.DefaultDatasetAdapterFactory;
import org.fluentjava.volundr.graph.jfreechart.ImageFactoryUsingJFreeChart;
import org.fluentjava.volundr.graph.jfreechart.JFreeChartWriter;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static java.util.Objects.requireNonNull;

@SuppressWarnings("PMD.UseProperClassLoader")
public class ScatterPlotBuilderTest {

    private static byte[] goldenMasterBytes;
    private static String targetPath;
    private static ImageFactoryUsingJFreeChart imageFactory;

    @BeforeClass
    public static void loadGoldenMaster() throws IOException, URISyntaxException {
        File goldenMaster = new File(
                requireNonNull(FrequencyGraphBuilderTest.class
                        .getResource("/plotGoldenMaster.png")).toURI());
        goldenMasterBytes = Files
                .readAllBytes(Paths.get(goldenMaster.getPath()));
        targetPath = System.getProperty("user.dir") + "/target";

        imageFactory = new ImageFactoryUsingJFreeChart(
                new JFreeChartWriter(targetPath));
    }

    @Test
    public void plotTest() throws IOException {
        DefaultDatasetAdapterFactory scatterPlotAdapterProvider = new DefaultDatasetAdapterFactory();

        ScatterPlotData scatterPlotData = ScatterPlotBuilder.newScatterPlotData(
                "graphTitle", "Temperature (Celsius) ", "Sales ($)",
                "legendTitle", scatterPlotAdapterProvider);

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

        String graphName = "plotTest";
        ScatterPlotBuilder.newScatterPlotBuilder(scatterPlotData)
                .writeGraph(imageFactory, graphName);

        byte[] bytes = Files
                .readAllBytes(Paths.get(targetPath, graphName + ".png"));
        Assert.assertArrayEquals(goldenMasterBytes, bytes);

    }
}
