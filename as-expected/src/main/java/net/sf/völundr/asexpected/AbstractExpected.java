package net.sf.völundr.asexpected;

import static org.junit.Assert.assertEquals;

/**
 * Original idea for this was got from Ville Oikarinen.
 */
public abstract class AbstractExpected<PARENT> implements Expected<PARENT> {
    private final StringBuilder expected = new StringBuilder();

    @Override
    public Expected<PARENT> string(final String string) {
        expected().append(string);
        return this;
    }

    @Override
    public PARENT end() {
        final String buildExpected = buildExpected();
        final String actual = actual();
        if (!buildExpected.equals(actual)) {
            System.err.println(
                    "If the actual output is what you want, copy-paste this to the test:\n"
                            + toTestExpectationCode(actual));
        }
        assertEquals(buildExpected, actual);
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
