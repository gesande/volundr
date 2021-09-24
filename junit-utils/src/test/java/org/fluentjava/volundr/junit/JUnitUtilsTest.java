package org.fluentjava.volundr.junit;

import org.junit.Test;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.TestClass;

import java.lang.annotation.*;
import java.util.List;
import java.util.function.Predicate;

import static org.junit.Assert.assertEquals;

public class JUnitUtilsTest {

    @Collect
    @Present
    @Test
    public void annotionPresent() {
        final List<FrameworkMethod> annotatedAsTest = new TestClass(
                this.getClass()).getAnnotatedMethods(Collect.class);
        assertEquals(2, annotatedAsTest.size());
        final TestSet<Annotation> pred = new TestSet<>(present());
        JUnitUtils.removeTestMethods(annotatedAsTest, pred.negate());
        assertEquals(1, annotatedAsTest.size());
        assertEquals("annotionPresent", annotatedAsTest.get(0).getName());
    }

    @Collect
    @NotPresent
    @Test
    public void annotionNotPresent() {
        final List<FrameworkMethod> annotatedAsTest = new TestClass(
                this.getClass()).getAnnotatedMethods(Collect.class);
        assertEquals(2, annotatedAsTest.size());
        final TestSet<Annotation> pred = new TestSet<>(notPresent());
        JUnitUtils.removeTestMethods(annotatedAsTest, pred.negate());
        assertEquals(1, annotatedAsTest.size());
        assertEquals("annotionNotPresent", annotatedAsTest.get(0).getName());
    }

    @SuppressWarnings({"unchecked", "static-method"})
    private <T extends Annotation> Class<T> notPresent() {
        return (Class<T>) NotPresent.class;
    }

    @SuppressWarnings({"unchecked", "static-method"})
    private <T extends Annotation> Class<T> present() {
        return (Class<T>) Present.class;
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.METHOD})
    @interface Collect { //
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.METHOD})
    @interface NotPresent { //
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.METHOD})
    @interface Present { //
    }

    private final static class TestSet<T extends Annotation>
            implements Predicate<FrameworkMethod> {

        private final Class<T> testSet;

        public TestSet(final Class<T> testSet) {
            this.testSet = testSet;
        }

        //this is not a test but a predicate implementation
        @SuppressWarnings("PMD.JUnit4TestShouldUseTestAnnotation")
        @Override
        public boolean test(final FrameworkMethod m) {
            T annotation = m.getAnnotation(testSet());
            if (annotation == null) {
                annotation = m.getMethod().getDeclaringClass()
                        .getAnnotation(testSet());
            }
            return annotation != null;
        }

        private Class<T> testSet() {
            return this.testSet;
        }

    }
}
