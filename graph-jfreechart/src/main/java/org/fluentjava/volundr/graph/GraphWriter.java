package org.fluentjava.volundr.graph;

public interface GraphWriter {

    boolean hasSomethingToWrite();

    String id();

    void writeImage(final ImageFactory imageFactory);

}
