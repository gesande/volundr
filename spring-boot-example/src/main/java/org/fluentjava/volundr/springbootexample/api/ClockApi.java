package org.fluentjava.volundr.springbootexample.api;

import org.fluentjava.volundr.springbootexample.dto.ClockResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Clock;

@RestController
public class ClockApi {
    @GetMapping("/clock")
    public ClockResponse get(@RequestParam(value = "from", defaultValue = "value") String from) {
        return new ClockResponse(Clock.systemUTC(), from);
    }

}
