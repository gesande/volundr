package org.fluentjava.volundr.fileio;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

public final class FileWriter {

    public void writeToFile(final File parent, final String contents,
            final String newFile, Charset charset) throws IOException {
        writeToFile(toFile(parent, newFile), contents, charset);
    }

    public void writeToFile(final File fileToWrite, final String contents,
            Charset charset) throws IOException {
        Files.writeString(Paths.get(fileToWrite.toURI()), contents, charset);
    }

    private static File toFile(final File parent, final String fileName) {
        return new File(parent, fileName);
    }
}
