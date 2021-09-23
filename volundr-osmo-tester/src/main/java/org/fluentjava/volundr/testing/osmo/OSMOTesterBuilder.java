package org.fluentjava.volundr.testing.osmo;

import osmo.tester.OSMOTester;
import osmo.tester.generator.algorithm.FSMTraversalAlgorithm;
import osmo.tester.generator.algorithm.WeightedBalancingAlgorithm;
import osmo.tester.generator.endcondition.EndCondition;
import osmo.tester.generator.endcondition.Length;
import osmo.tester.reporting.jenkins.JenkinsReportGenerator;

public final class OSMOTesterBuilder {
    private final OSMOTester tester;

    private OSMOTesterBuilder(final OSMOTester osmoTester) {
        this.tester = osmoTester;
    }

    public Runnable build(long seed) {
        return () -> tester.generate(seed);
    }

    public OSMOTesterBuilder endCondition(EndCondition condition) {
        tester.setTestEndCondition(condition);
        return this;
    }

    public OSMOTesterBuilder suiteEndCondition(EndCondition condition) {
        tester.setSuiteEndCondition(condition);
        return this;
    }

    public OSMOTesterBuilder models(OsmoModel... models) {
        for (OsmoModel model : models) {
            this.tester.addModelObject(model);
        }
        return this;
    }

    public OSMOTesterBuilder algorithm(FSMTraversalAlgorithm algorithm) {
        this.tester.setAlgorithm(algorithm);
        return this;
    }

    public OSMOTesterBuilder withJenkinsReport(String reportName) {
        this.tester.addListener(new JenkinsReportGenerator(reportName, true));
        return this;
    }

    public static OSMOTesterBuilder builder() {
        return new OSMOTesterBuilder(new OSMOTester());
    }

    public OSMOTesterBuilder weightedBalancingAlgorithm() {
        return algorithm(new WeightedBalancingAlgorithm());
    }

    public OSMOTesterBuilder oneSuite() {
        return suiteEndCondition(new Length(1));
    }
}
