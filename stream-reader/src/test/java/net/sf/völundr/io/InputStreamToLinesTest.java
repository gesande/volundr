package net.sf.völundr.io;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import net.sf.völundr.LineVisitor;

import org.junit.Test;

public class InputStreamToLinesTest {

    @SuppressWarnings("static-method")
    @Test
    public void visit() throws IOException {
        final String lines = "line1\nline2\nline3";
        final Charset charset = Charset.defaultCharset();
        final InputStream inputStream = toByteArrayStream(lines, charset);
        final List<String> values = new ArrayList<String>();
        new InputStreamToLines(new LineVisitor() {
            @Override
            public void visit(final String line) {
                values.add(line);
            }

            @Override
            public void emptyLine() {
                throw new FailIHave("You shouldn't come here!");
            }
        }, charset).readFrom(inputStream);

        assertEquals(3, values.size());
        assertTrue(values.contains("line1"));
        assertTrue(values.contains("line2"));
        assertTrue(values.contains("line3"));
        assertEquals("line1", values.get(0));
        assertEquals("line2", values.get(1));
        assertEquals("line3", values.get(2));
    }

    @SuppressWarnings("static-method")
    @Test
    public void visitEmptyLines() throws IOException {
        final String lines = "line1\nline2\n\nline3";
        final List<String> values = new ArrayList<String>();
        final Charset charset = Charset.defaultCharset();
        new InputStreamToLines(new LineVisitor() {
            @Override
            public void visit(final String line) {
                values.add(line);
            }

            @Override
            public void emptyLine() {
                values.add("empty line");
            }
        }, charset).readFrom(toByteArrayStream(lines, charset));

        assertEquals(4, values.size());
        assertTrue(values.contains("line1"));
        assertTrue(values.contains("line2"));
        assertTrue(values.contains("empty line"));
        assertTrue(values.contains("line3"));
        assertEquals("line1", values.get(0));
        assertEquals("line2", values.get(1));
        assertEquals("empty line", values.get(2));
        assertEquals("line3", values.get(3));
    }

    @SuppressWarnings("static-method")
    @Test
    public void whenSomethingGoesWrongVisitingLine() {
        final String lines = "line1\nline2\nline3";
        AtomicBoolean failed = new AtomicBoolean(false);
        Charset charset = Charset.defaultCharset();
        final StreamReader reader = new InputStreamToLines(new LineVisitor() {

            @Override
            public void visit(final String line) {
                throw new FailIHave("Visiting line:" + line + " failed!");
            }

            @Override
            public void emptyLine() {
                throw new FailIHave("You shouldn't come here!");
            }
        }, charset);
        try {
            reader.readFrom(toByteArrayStream(lines, charset));
        } catch (Exception ex) {
            failed.set(true);
            assertEquals(FailIHave.class, ex.getClass());
            assertEquals("Visiting line:line1 failed!", ex.getMessage());
        }
    }

    @SuppressWarnings("static-method")
    public void whenSomethingGoesWrongVisitingEmptyLine() {
        final AtomicBoolean failed = new AtomicBoolean(false);
        final String lines = "line1\n\nline2\nline3";
        Charset charset = Charset.defaultCharset();
        final StreamReader reader = new InputStreamToLines(new LineVisitor() {
            @Override
            public void visit(final String line) {
                //
            }

            @Override
            public void emptyLine() {
                throw new FailIHave("Empty line fails");
            }
        }, charset);
        try {
            reader.readFrom(toByteArrayStream(lines, charset));
        } catch (Exception ex) {
            failed.set(true);
            assertEquals("Empty line fails", ex.getMessage());
            assertEquals(FailIHave.class, ex.getClass());
        }
        assertTrue(failed.get());
    }

    private static InputStream toByteArrayStream(final String value,
            final Charset charset) {
        return new ToByteArrayStream(charset).toByteArrayStream(value);
    }

}
