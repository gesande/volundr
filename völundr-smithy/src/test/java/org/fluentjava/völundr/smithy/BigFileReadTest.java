package org.fluentjava.völundr.smithy;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.fluentjava.völundr.LineVisitor;
import org.fluentjava.völundr.io.StreamReader;
import org.junit.Test;

public class BigFileReadTest {

    @Test
    public void readOnePointFiveMillionRows() throws IOException {
        final VölundrSmithy völundrSmithy = new VölundrSmithy(
                Charset.forName("UTF-8"));
        final AtomicInteger lines = new AtomicInteger(0);
        final long start = System.currentTimeMillis();
        völundrSmithy.inputStreamToLines(new LineVisitor() {

            @Override
            public void visit(final String line) {
                lines.incrementAndGet();
            }

            @Override
            public void emptyLine() {
                lines.incrementAndGet();
            }
        }).readFrom(resourceAsStream("file-with-1point5million-lines"));
        final long duration = System.currentTimeMillis() - start;
        System.out.println("1,5 million lines read at " + duration + " ms.");
        assertTrue("Duration should be no more 600 ms for 1,5 million rows",
                duration <= 600);
        assertEquals(1500000, lines.get());
    }

    @Test
    public void readingWith() {
        final VölundrSmithy völundrSmithy = new VölundrSmithy(
                Charset.forName("UTF-8"));
        final AtomicInteger lines = new AtomicInteger(0);
        final long start = System.currentTimeMillis();
        StreamReader reader = völundrSmithy
                .inputStreamToLines(new LineVisitor() {

                    @Override
                    public void visit(final String line) {
                        lines.incrementAndGet();
                    }

                    @Override
                    public void emptyLine() {
                        lines.incrementAndGet();
                    }
                });
        völundrSmithy.readStreamsWith(1, 5000, TimeUnit.MILLISECONDS, reader,
                resourceAsStream("file-with-1point5million-lines"),
                resourceAsStream("file-with-1point5million-lines2"),
                resourceAsStream("file-with-1point5million-lines3"),
                resourceAsStream("file-with-1point5million-lines4"));
        final long duration = System.currentTimeMillis() - start;
        System.out.println("6 million lines read at " + duration + " ms.");
        assertTrue("Duration should be no more that 1500 ms for 6 million rows",
                duration <= 2000);

        assertEquals(6000000, lines.get());
    }

    private static InputStream resourceAsStream(final String resource) {
        return BigFileReadTest.class.getResourceAsStream("/" + resource);
    }

}
