package org.fluentjava.volundr.graph;

public interface GraphWriter {

    public boolean hasSomethingToWrite();

    public String id();

    public void writeImage(final ImageFactory imageFactory);

}
