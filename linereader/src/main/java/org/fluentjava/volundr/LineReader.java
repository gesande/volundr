package org.fluentjava.volundr;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

public final class LineReader {
    private final Charset charset;

    public LineReader(final Charset charset) {
        this.charset = charset;
    }

    @SuppressWarnings("PMD.AssignmentInOperand")
    public void read(final InputStream stream, final LineVisitor visitor)
            throws IOException {
        try (DataInputStream in = new DataInputStream(stream)) {
            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(in, charSet()))) {
                String strLine;
                while ((strLine = br.readLine()) != null) {
                    if (strLine.isEmpty()) {
                        visitor.emptyLine();
                    } else {
                        visitor.visit(strLine);
                    }
                }
            }
        }
    }

    private Charset charSet() {
        return this.charset;
    }
}
