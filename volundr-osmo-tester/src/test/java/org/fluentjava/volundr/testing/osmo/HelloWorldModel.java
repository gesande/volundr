package org.fluentjava.volundr.testing.osmo;

import java.util.concurrent.atomic.AtomicInteger;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.extern.slf4j.Slf4j;
import osmo.tester.annotation.Guard;
import osmo.tester.annotation.TestStep;

@SuppressFBWarnings("EI_EXPOSE_REP2")
@Slf4j
public class HelloWorldModel implements OsmoModel {
    private final AtomicInteger calls;

    public HelloWorldModel(AtomicInteger calls) {
        this.calls = calls;
    }

    @Guard({ "hello" })
    public boolean helloGuard() {
        return true; // you can always call hello step
    }

    @TestStep("hello")
    public void hello() {
        calls.incrementAndGet();
        log.info("Hello {}", calls.get());
    }
}
