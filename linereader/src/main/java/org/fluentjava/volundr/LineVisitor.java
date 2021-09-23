package org.fluentjava.volundr;

public interface LineVisitor {
    void visit(final String line);

    void emptyLine();
}
