package org.fluentjava.volundr.fileio;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.junit.Test;

public class FileUtilTest {

    @Test
    public void writeToFile() throws IOException {
        byte[] expected = content().toString()
                .getBytes(Charset.defaultCharset());
        Path dir = Files.createTempDirectory(UUID.randomUUID().toString());

        String fileName = dir.toFile().getAbsolutePath() + "/content";
        FileUtil.writeToFile(fileName, expected);
        byte[] actual = Files.readAllBytes(Paths.get(fileName));
        assertArrayEquals(expected, actual);
    }

    @Test
    public void ensureDirectoryExists() throws IOException {
        Path dir = Files.createTempDirectory(UUID.randomUUID().toString());
        FileUtil.ensureDirectoryExists(new File(
                dir.toFile().getAbsolutePath() + "/daapa", "diipa/directory"));
        assertTrue(Files.exists(Paths.get(
                dir.toFile().getAbsolutePath() + "/daapa/diipa/directory")));
    }

    private static StringBuilder content() {
        return new StringBuilder("content");
    }
}
