package net.sf.völundr.fileio;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.charset.Charset;

import org.junit.Test;

public class FileUtilTest {

    @Test
    public void writeToFile() throws WritingFileFailed {
        FileUtil.writeToFile("./target/content",
                content().toString().getBytes(Charset.defaultCharset()));
    }

    @Test
    public void ensureDirectoryExists()
            throws FileNotFoundException, DirectoryNotCreatedException {
        FileUtil.ensureDirectoryExists(
                new File("./target/daapa", "diipa/directory"));
    }

    private static StringBuilder content() {
        return new StringBuilder("content");
    }
}
