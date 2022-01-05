package org.fluentjava.volundr.smithy;

import lombok.extern.slf4j.Slf4j;
import org.fluentjava.volundr.LineVisitor;
import org.fluentjava.volundr.io.StreamReader;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@Slf4j
public class BigFileReadTest {

    @Test
    public void readOnePointFiveMillionRows() throws IOException {
        final VolundrSmithy volundrSmithy = new VolundrSmithy(UTF_8);
        final AtomicInteger lines = new AtomicInteger(0);
        final long start = System.currentTimeMillis();
        volundrSmithy.inputStreamToLines(new LineVisitor() {

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
        log.info("1,5 million lines read at {} ms.", duration);
        assertTrue("Duration should be no more 600 ms for 1,5 million rows",
                duration <= 600);
        assertEquals(1500000, lines.get());
    }

    @Test
    public void readingWith() {
        final VolundrSmithy volundrSmithy = new VolundrSmithy(UTF_8);
        final AtomicInteger lines = new AtomicInteger(0);
        final long start = System.currentTimeMillis();
        StreamReader reader = volundrSmithy
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
        volundrSmithy.readStreamsWith(1, 5000, TimeUnit.MILLISECONDS, reader,
                resourceAsStream("file-with-1point5million-lines"),
                resourceAsStream("file-with-1point5million-lines2"),
                resourceAsStream("file-with-1point5million-lines3"),
                resourceAsStream("file-with-1point5million-lines4"));
        final long duration = System.currentTimeMillis() - start;
        log.info("6 million lines read at {} ms.", duration);
        assertTrue("Duration should be no more that 1500 ms for 6 million rows",
                duration <= 2000);

        assertEquals(6000000, lines.get());
    }

    private static InputStream resourceAsStream(final String resource) {
        return BigFileReadTest.class.getResourceAsStream("/" + resource);
    }

}
