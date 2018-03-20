package org.fluentjava.völundr.graph;

public interface GraphStatisticsProvider {

    int median();

    double mean();

    int percentile95();

}
