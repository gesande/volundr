package org.fluentjava.volundr.fileio;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

import org.fluentjava.volundr.io.StringToBytes;
import org.junit.Test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FileAppenderTest {
    @Test
    public void appendTest() throws WritingFileFailed, IOException {
        AtomicInteger failures = new AtomicInteger(0);
        AtomicInteger ok = new AtomicInteger(0);
        AtomicInteger started = new AtomicInteger(0);
        FileAppender appender = new FileAppender(
                StringToBytes.forCharset(UTF_8), new FileAppendHandler() {
                    @Override
                    public void failed(String file, AppendToFileFailed e) {
                        failures.incrementAndGet();
                        log.error("Append failed", e);
                    }

                    @Override
                    public void ok(String file) {
                        ok.incrementAndGet();
                    }

                    @Override
                    public void start(String file) {
                        started.incrementAndGet();
                    }
                });

        String file = UUID.randomUUID().toString();
        String fileName = "./target/append-test-" + file;
        FileUtil.writeToFile(fileName, "first".getBytes(UTF_8));
        appender.appendToFile(fileName, "appended");
        assertEquals(0, failures.get());
        assertEquals(1, started.get());
        assertEquals(1, ok.get());
        byte[] actual = Files.readAllBytes(Paths.get(fileName));
        byte[] expected = "firstappended".getBytes(UTF_8);
        assertArrayEquals(expected, actual);
    }
}
