package org.fluentjava.volundr.graph;

public interface GraphStatisticsProvider {

    int median();

    double mean();

    int percentile95();

}
