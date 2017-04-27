package net.sf.völundr.asexpected;

public final class AsExpected<PARENT> extends AbstractExpected<PARENT> {

    private final String actual;
    private final PARENT parent;

    public AsExpected(final String actual, final PARENT parent) {
        this.actual = actual;
        this.parent = parent;
    }

    @Override
    protected PARENT parent() {
        return this.parent;
    }

    @Override
    protected String actual() {
        return this.actual;
    }

    public static AsExpected<Void> expected(final String actual) {
        return new AsExpected<>(actual, null);
    }
}