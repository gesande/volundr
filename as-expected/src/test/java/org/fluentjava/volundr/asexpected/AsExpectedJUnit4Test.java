package org.fluentjava.volundr.asexpected;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * AsExpected tests and outputs with JUnit 4
 */
public class AsExpectedJUnit4Test {

    @Test
    public void expected() {
        final AsExpected<AsExpectedJUnit4Test> expected = AsExpected
                .expected("actual", this, assertFunc());
        Assertions.assertEquals(this, expected.string("actual").end());
    }

    @Test
    public void lineExpected() {
        final AsExpected<AsExpectedJUnit4Test> expected = AsExpected
                .expected("actual\n", this, assertFunc());
        Assertions.assertEquals(this, expected.line("actual").end());
    }

    @Test
    public void notExpected() {
        final AsExpected<Void> expected = AsExpected.expected("actual\n",
                assertFunc());
        assertThrows(AssertionError.class,
                () -> expected.line("not actual").end(), """
                        expected:<[not ]actual
                        > but was:<[]actual
                        >""");
    }

    @Test
    public void expectedWithSpecialCharacters() {
        final AsExpected<Void> expected = AsExpected.expected("actuál\n",
                assertFunc());
        assertThrows(AssertionError.class,
                () -> expected.line("not actual").end(), """
                        expected:<actu[a]l
                        > but was:<actu[á]l
                        >""");
    }

    @Test
    public void expectedNull() {
        final AsExpected<AsExpectedJUnit4Test> expected = AsExpected
                .expected(null, this, assertFunc());
        assertThrows(AssertionError.class, () -> expected.string("daa").end(),
                "");
    }

    /**
     * Assert func with JUnit 4. Don't fix 'Lambda can be replaced with method
     * reference' because lint
     */
    private AssertFunc assertFunc() {
        return (expected, actual) -> assertEquals(expected, actual);
    }
}
