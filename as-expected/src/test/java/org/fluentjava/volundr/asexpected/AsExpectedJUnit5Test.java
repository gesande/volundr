package org.fluentjava.volundr.asexpected;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * AsExpected tests and outputs with JUnit 4
 */
public class AsExpectedJUnit5Test {

    @Test
    public void expected() {
        final AsExpected<AsExpectedJUnit5Test> expected = AsExpected
                .expected("actual", this, Assertions::assertEquals);
        assertEquals(this, expected.string("actual").end());
    }

    @Test
    public void lineExpected() {
        final AsExpected<AsExpectedJUnit5Test> expected = AsExpected
                .expected("actual\n", this, Assertions::assertEquals);
        assertEquals(this, expected.line("actual").end());
    }

    @Test
    public void notExpected() {
        final AsExpected<Void> expected = AsExpected.expected("actual\n",
                Assertions::assertEquals);
        assertThrows(AssertionError.class,
                () -> expected.line("not actual").end(), """
                        expected:<[not ]actual
                        > but was:<[]actual
                        >""");
    }

    @Test
    public void expectedWithSpecialCharacters() {
        final AsExpected<Void> expected = AsExpected.expected("actuál\n",
                Assertions::assertEquals);
        assertThrows(AssertionError.class,
                () -> expected.line("not actual").end(), """
                        expected:<actu[a]l
                        > but was:<actu[á]l
                        >""");
    }

    @Test
    public void expectedNull() {
        final AsExpected<AsExpectedJUnit5Test> expected = AsExpected
                .expected(null, this, Assertions::assertEquals);
        assertThrows(AssertionError.class, () -> expected.string("daa").end(),
                "");
    }

}
