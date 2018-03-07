package net.sf.völundr.asexpected;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.concurrent.atomic.AtomicBoolean;

import org.junit.Test;

public class AsExpectedTest {

    @Test
    public void expected() {
        final AsExpected<Void> expected = AsExpected.expected("actual");
        expected.string("actual").end();
    }

    @Test
    public void lineExpected() {
        final AsExpected<Void> expected = AsExpected.expected("actual\n");
        expected.line("actual").end();
    }

    @Test
    public void notExpected() {
        final AsExpected<Void> expected = AsExpected.expected("actual\n");
        final AtomicBoolean failed = new AtomicBoolean(false);
        try {
            expected.line("not actual").end();
        } catch (final Throwable t) {
            assertEquals(
                    "expected:<[not ]actual\n" + "> but was:<[]actual\n" + ">",
                    t.getMessage());
            failed.set(true);
        }
        assertTrue(failed.get());
    }

    @Test
    public void asExpectedWithSpecialCharacters() {
        final AsExpected<Void> expected = AsExpected.expected("actuál\n");
        final AtomicBoolean failed = new AtomicBoolean(false);
        try {
            expected.line("actual").end();
        } catch (final Throwable t) {
            assertEquals("expected:<actu[a]l\n" + "> but was:<actu[á]l\n" + ">",
                    t.getMessage());
            failed.set(true);
        }
        assertTrue(failed.get());
    }
}
