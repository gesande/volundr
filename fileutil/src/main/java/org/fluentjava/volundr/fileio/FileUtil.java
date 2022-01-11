package org.fluentjava.volundr.fileio;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FileUtil {

    public static void writeToFile(final String path, final byte[] data)
            throws IOException {
        final File outFile = new File(path);
        log.debug("writeToFile path:{} bytes:{}", path, data.length);
        ensureDirectoryExists(outFile.getParentFile());
        Files.write(Paths.get(outFile.toURI()), data);
    }

    public static void ensureDirectoryExists(final File dir)
            throws IOException {
        log.debug("ensureDirectoryExists {}", dir);
        final File parent = dir.getParentFile();
        if (!dir.exists()) {
            if (parent == null) {
                throw new FileNotFoundException(
                        "Parent directory didn't exist!");
            }
            ensureDirectoryExists(parent);
            if (!dir.mkdir()) {
                throw new IOException(
                        "Directory '" + dir.getName() + "' wasn't created!");
            }
            log.debug("{} created ", dir);
        } else {
            log.debug("{} already exists", dir);
        }
    }

    public static void appendToFile(final String file, final byte[] bytes)
            throws IOException {
        log.debug("appendToFile file:{} bytes:{} ", file, bytes.length);
        Files.write(Paths.get(file), bytes, StandardOpenOption.APPEND);
    }
}
