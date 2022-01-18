package org.fluentjava.volundr.asexpected;

/**
 * Original idea for this was got from Ville Oikarinen.
 */
@SuppressWarnings("PMD.AvoidStringBufferField")
public abstract class AbstractExpected<PARENT> implements Expected<PARENT> {
    private final StringBuilder expected = new StringBuilder();
    private final AssertFunc assertFunc;

    protected AbstractExpected(AssertFunc assertFunc) {
        this.assertFunc = assertFunc;
    }

    @Override
    public Expected<PARENT> string(final String string) {
        expected().append(string);
        return this;
    }

    @SuppressWarnings("PMD.SystemPrintln")
    @Override
    public PARENT end() {
        final String buildExpected = buildExpected();
        final String actual = actual();
        if (!buildExpected.equals(actual)) {
            String msg = actual != null
                    ? "If the actual output is what you want, copy-paste this to the test:\n"
                            + toTestExpectationCode(actual)
                    : "Expectation was null";
            System.err.println(msg);
        }
        assertFunc.assertEquals(buildExpected, actual);
        return parent();
    }

    protected abstract PARENT parent();

    private static String toTestExpectationCode(final String s) {
        return s.replaceAll("\"", "\\\\\"")
                .replaceAll("(.*)\n", "expected.line(\"$1\");\n")
                .replaceAll(".line\\(\"\"\\)\n", "") + "expected.end();\n";
    }

    @Override
    public Expected<PARENT> line(final String line) {
        return string(line).string("\n");
    }

    protected abstract String actual();

    private String buildExpected() {
        return expected().toString();
    }

    private StringBuilder expected() {
        return this.expected;
    }

}
