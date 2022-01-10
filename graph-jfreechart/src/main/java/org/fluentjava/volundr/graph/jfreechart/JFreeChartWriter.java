package org.fluentjava.volundr.graph.jfreechart;

import java.io.File;

import org.fluentjava.volundr.fileio.FileUtil;
import org.fluentjava.volundr.graph.ChartWriter;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class JFreeChartWriter implements ChartWriter<JFreeChart> {

    private final String reportRootDirectory;

    public JFreeChartWriter(final String reportRootDirectory) {
        this.reportRootDirectory = reportRootDirectory;
    }

    @Override
    public void write(final String id, final JFreeChart chart, final int height,
            final int width) {
        final String outputFilePath = reportRootDirectory() + "/" + id + ".png";
        log.info("Writing chart as an image to file {}", outputFilePath);
        try {
            FileUtil.ensureDirectoryExists(newFile(reportRootDirectory()));
            ChartUtils.saveChartAsPNG(newFile(outputFilePath), chart, width,
                    height);
            log.info("Chart image successfully written to {}", outputFilePath);
        } catch (final Exception e) {
            final String errorMsg = "Writing file '" + outputFilePath
                    + "' failed!";
            log.error(errorMsg, e);
            throw new VolundrRuntimeException(errorMsg, e);
        }
    }

    private static File newFile(final String path) {
        return new File(path);
    }

    private String reportRootDirectory() {
        return this.reportRootDirectory;
    }

}
