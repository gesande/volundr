package org.fluentjava.völundr.graph;

public interface LineChartAdapterProvider<GRAPHDATA, PAINT> {

    DatasetAdapter<GRAPHDATA, PAINT> forLineChart(String title);

}
