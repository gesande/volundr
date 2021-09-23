package org.fluentjava.volundr.graph;

public interface LineChartAdapterProvider<GRAPHDATA, PAINT> {

    DatasetAdapter<GRAPHDATA, PAINT> forLineChart(String title);

}
