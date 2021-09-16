package org.fluentjava.volundr.fileio;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class FileUtil {
    @SuppressWarnings("PMD.AvoidCatchingThrowable")
    public static void writeToFile(final String path, final byte[] data)
            throws WritingFileFailed {
        final File outFile = new File(path);
        try {
            ensureDirectoryExists(outFile.getParentFile());
            writeToFile(outFile, data);
        } catch (final Throwable cause) {
            throw newWritingFileFailed(path, cause);
        }
    }

    private static void writeToFile(final File outFile, final byte[] data)
            throws IOException {
        writeAndClose(Files.newOutputStream(Paths.get(outFile.toURI())), data);
    }

    private static void writeAndClose(final OutputStream fos, final byte[] data)
            throws IOException {
        try {
            fos.write(data);
            fos.flush();
        } finally {
            fos.close();
        }
    }

    private static WritingFileFailed newWritingFileFailed(final String path,
            final Throwable cause) {
        return new WritingFileFailed("Couldn't write to file '" + path + "' ",
                cause);
    }

    public static void ensureDirectoryExists(final File dir)
            throws FileNotFoundException, DirectoryNotCreatedException {
        final File parent = dir.getParentFile();
        if (!dir.exists()) {
            if (parent == null) {
                throw new FileNotFoundException(
                        "Parent directory didn't exist!");
            }
            ensureDirectoryExists(parent);
            if (!dir.mkdir()) {
                throw new DirectoryNotCreatedException(
                        "Directory '" + dir.getName() + "' wasn't created!");
            }
        }
    }

    @SuppressWarnings("PMD.AvoidCatchingThrowable")
    public static void appendToFile(final String file, final byte[] bytes)
            throws AppendToFileFailed {
        try {
            writeAndClose(Files.newOutputStream(Paths.get(file),
                    StandardOpenOption.APPEND), bytes);
        } catch (final Throwable t) {
            throw new AppendToFileFailed(
                    "Failed to append to file '" + file + "' ", t);
        }
    }
}
