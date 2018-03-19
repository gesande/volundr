package org.fluentjava.völundr.graph.frequency;

public interface FrequencyData {

    long max();

    long countFor(long value);

    boolean hasSamples();

}
