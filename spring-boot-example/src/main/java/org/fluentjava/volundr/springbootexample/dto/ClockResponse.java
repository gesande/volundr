package org.fluentjava.volundr.springbootexample.dto;

import java.time.Clock;

public class ClockResponse {
    private final String instant;
    private final String from;
    private final String millis;

    public ClockResponse(Clock clock, String from) {
        this.millis = Long.toString(clock.millis());
        this.instant = clock.instant().toString();
        this.from = from;
    }

    public String getContent() {
        return millis;
    }

    public String getInstant() {
        return instant;
    }

    public String getFrom() {
        return from;
    }
}
