package org.fluentjava.völundr.junit;

import java.util.Iterator;
import java.util.List;

import org.fluentjava.völundr.junit.predicates.Predicate;
import org.junit.runners.model.FrameworkMethod;

/**
 * This will not work after JUnit 4.11 since those collections will be
 * read-only.
 */
public final class JUnitUtils {

    public static void removeTestMethods(final List<FrameworkMethod> list,
            final Predicate<FrameworkMethod> predicate) {
        filter(list, predicate);
    }

    private static <T> void filter(final Iterable<T> iterable,
            final Predicate<T> predicate) {
        final Iterator<T> iter = iterable.iterator();

        while (iter.hasNext()) {
            if (predicate.apply(iter.next())) {
                iter.remove();
            }
        }
    }

    private JUnitUtils() {
    }
}
