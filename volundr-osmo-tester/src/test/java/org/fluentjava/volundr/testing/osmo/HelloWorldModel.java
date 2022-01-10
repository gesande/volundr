package org.fluentjava.volundr.testing.osmo;

import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import osmo.tester.annotation.Guard;
import osmo.tester.annotation.TestStep;

@SuppressFBWarnings("EI_EXPOSE_REP2")
public class HelloWorldModel implements OsmoModel {
    private final static Logger LOGGER = LoggerFactory
            .getLogger(HelloWorldModel.class);
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
        LOGGER.info("Hello {}", calls.get());
    }
}
