package org.fluentjava.völundr.testing.osmo;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import osmo.tester.generator.testsuite.TestSuite;

public abstract class AbstractOsmoModel implements OsmoModel {

    @SuppressFBWarnings(value = "UWF_UNWRITTEN_PUBLIC_OR_PROTECTED_FIELD", justification = "Osmo test tool makes sure field is set")
    protected TestSuite suite;

    protected String totalTestsGeneratedMessage() {
        int tests = suite.getAllTestCases().size() + 1;
        return "Total tests generated:" + tests;
    }

    protected final String currentTestStepName() {
        return suite.getCurrentTest().getCurrentStep().getName();
    }

}
