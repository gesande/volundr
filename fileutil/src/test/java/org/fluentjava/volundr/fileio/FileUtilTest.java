package org.fluentjava.volundr.fileio;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.Test;

public class FileUtilTest {

    @Test
    public void writeToFile() throws WritingFileFailed, IOException {
        byte[] expected = content().toString()
                .getBytes(Charset.defaultCharset());
        FileUtil.writeToFile("./target/content", expected);
        byte[] actual = Files.readAllBytes(Paths.get("./target/content"));
        assertArrayEquals(expected, actual);
    }

    @Test
    public void ensureDirectoryExists()
            throws FileNotFoundException, DirectoryNotCreatedException {
        FileUtil.ensureDirectoryExists(
                new File("./target/daapa", "diipa/directory"));
        assertTrue(Files.exists(Paths.get("./target/daapa/diipa/directory")));
    }

    private static StringBuilder content() {
        return new StringBuilder("content");
    }
}
