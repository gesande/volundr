package org.fluentjava.volundr.asexpected;

public final class AsExpected<PARENT> extends AbstractExpected<PARENT> {

    private final String actual;
    private final PARENT parent;

    public AsExpected(final String actual, final PARENT parent,
            AssertFunc assertFunc) {
        super(assertFunc);
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

    public static AsExpected<Void> expected(final String actual,
            AssertFunc assertFunc) {
        return new AsExpected<>(actual, null, assertFunc);
    }

    public static <T> AsExpected<T> expected(final String actual, T parent,
            AssertFunc assertFunc) {
        return new AsExpected<>(actual, parent, assertFunc);
    }
}
