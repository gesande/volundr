package org.fluentjava.volundr.graph;

public interface GraphWriterProvider {
    GraphWriter graphWriterFor(final String id);
}
