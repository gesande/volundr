package org.fluentjava.völundr.io;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class StringToByteArrayInputStream implements StringToInputStream {

    private final StringToBytes stringToBytes;

    public StringToByteArrayInputStream(final StringToBytes stringToBytes) {
        this.stringToBytes = stringToBytes;
    }

    @Override
    public InputStream fromString(final String value) {
        return new ByteArrayInputStream(this.stringToBytes.convert(value));
    }

}
