package org.fluentjava.volundr.testing.osmo.statistics;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

import java.util.Random;

import static java.lang.Math.abs;

@FunctionalInterface
public interface SleepValueProvider {
    long get();

    /**
     * Calculates next sleep value between averageSleepInMillis - averageSleepInMillis*2
     */
    @SuppressFBWarnings({ "RV_ABSOLUTE_VALUE_OF_RANDOM_INT", "DMI_RANDOM_USED_ONLY_ONCE" })
    static long calculateNextSleepValue(long averageSleepInMillis, Random random) {
        return abs(random.nextLong()) % (averageSleepInMillis * 2L);
    }

}
