package org.fluentjava.volundr;

import java.util.regex.Pattern;

public final class StringSplitter {

    private final Pattern pattern;

    public StringSplitter(final String regex) {
        this.pattern = Pattern.compile(regex);
    }

    public String[] split(final String input) {
        return pattern().split(input);
    }

    private Pattern pattern() {
        return this.pattern;
    }
}
