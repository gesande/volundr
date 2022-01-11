package org.fluentjava.volundr.fileio;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import org.fluentjava.volundr.io.StringToBytes;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class FileAppender {
    private final FileAppendHandler handler;
    private final StringToBytes toBytes;

    public FileAppender(final StringToBytes toBytes,
            final FileAppendHandler handler) {
        this.toBytes = requireNonNull(toBytes);
        this.handler = requireNonNull(handler);
    }

    public void appendToFile(final String file, final String data) {
        log.debug("appendToFile file:{} data length:{}", file, data.length());
        handler.start(file);
        try {
            Files.write(Paths.get(file), toBytes.convert(data),
                    StandardOpenOption.APPEND);
            handler.ok(file);
        } catch (final IOException e) {
            handler.failed(file, e);
        }
    }

}
