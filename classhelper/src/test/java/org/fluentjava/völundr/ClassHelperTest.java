package org.fluentjava.völundr;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.fluentjava.shouldfind.ThisShouldBeFound;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClassHelperTest {
    private final static Logger LOG = LoggerFactory
            .getLogger(ClassHelperTest.class);

    @Test
    public void classesForAPackage()
            throws IOException, ClassNotFoundException {
        final Class<?>[] classes = ClassHelper
                .getClasses("org.fluentjava.shouldfind");
        LOG.debug("Classes = {}", Arrays.toString(classes));
        final List<Class<?>> classList = new ArrayList<>();
        for (final Class<?> clazz : classes) {
            classList.add(clazz);
        }
        assertTrue(classList.contains(ThisShouldBeFound.class));
    }

    @Test
    public void classesForAPackageRecursive()
            throws IOException, ClassNotFoundException {
        final Class<?>[] classes = ClassHelper.getClasses("org.fluentjava");
        LOG.debug("Classes = {}", Arrays.toString(classes));
        final List<Class<?>> classList = new ArrayList<>();
        for (final Class<?> clazz : classes) {
            classList.add(clazz);
        }
        assertTrue(classList.contains(ThisShouldBeFound.class));
        assertTrue(classList.contains(ClassHelperTest.class));
    }

    @Test
    public void classesForAPackageContainingUtf8Characters()
            throws IOException, ClassNotFoundException {
        final Class<?>[] classes = ClassHelper
                .getClasses("org.fluentjava.völundr");
        LOG.debug("Classes = {}", Arrays.toString(classes));
        final List<Class<?>> classList = new ArrayList<>();
        for (final Class<?> clazz : classes) {
            classList.add(clazz);
        }
        assertTrue(classList.contains(ClassHelperTest.class));
    }

    @Test
    public void noClassesForAnUnknownSubPackage()
            throws IOException, ClassNotFoundException {
        assertEquals(0,
                ClassHelper.getClasses("org.fluentjava.unknown").length);
    }

    @Test
    public void noClassesForAnEmptyPackage()
            throws IOException, ClassNotFoundException {
        assertEquals(0,
                ClassHelper.getClasses("org.fluentjava.emptypackage").length);
    }

    @Test
    public void noClassesForAnUnknownPackage()
            throws IOException, ClassNotFoundException {
        assertEquals(0, ClassHelper.getClasses("fail.i.have").length);
    }
}
