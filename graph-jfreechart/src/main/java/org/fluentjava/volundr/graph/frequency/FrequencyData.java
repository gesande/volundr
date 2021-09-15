package org.fluentjava.volundr.graph.frequency;

public interface FrequencyData {

    long max();

    long countFor(long value);

    boolean hasSamples();

}
