package org.fluentjava.volundr.fileio;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

public final class FileWriter {

    public void writeToFile(final File parent, final String contents,
            final String newFile, Charset charset) throws IOException {
        writeToFile(toFile(parent, newFile), contents, charset);
    }

    @SuppressWarnings("static-method")
    public void writeToFile(final File fileToWrite, final String contents,
            Charset charset) throws IOException {
        write(withWriter(fileToWrite, charset), contents);
    }

    private static Writer withWriter(final File file, final Charset charset)
            throws IOException {
        return new BufferedWriter(writerFor(file, charset));
    }

    private static Writer writerFor(final File file, Charset charset)
            throws IOException {
        return new OutputStreamWriter(
                Files.newOutputStream(Paths.get(file.toURI())), charset);
    }

    private static File toFile(final File parent, final String fileName) {
        return new File(parent, fileName);
    }

    private static void write(final Writer out, final String contents)
            throws IOException {
        try (out) {
            out.write(contents);
        }
    }
}
