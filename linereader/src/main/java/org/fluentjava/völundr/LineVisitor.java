package org.fluentjava.völundr;

public interface LineVisitor {
    void visit(final String line);

    void emptyLine();
}
