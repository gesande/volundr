package org.fluentjava.volundr.springbootexample;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Clock;
import java.time.LocalTime;

@RestController
public class ClockController {
    @GetMapping("/clock")
    public ClockResponse get(@RequestParam(value = "from", defaultValue = "value") String from) {
        return new ClockResponse(Clock.systemUTC(), from);
    }

}
