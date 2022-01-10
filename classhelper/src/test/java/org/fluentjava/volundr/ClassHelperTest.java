package org.fluentjava.volundr;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.fluentjava.shouldfind.ThisShouldBeFound;
import org.fluentjava.völundr.ContainingUtf8Characters;
import org.junit.Test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ClassHelperTest {

    @Test
    public void classesForAPackage()
            throws IOException, ClassNotFoundException {
        final Class<?>[] classes = ClassHelper
                .getClasses("org.fluentjava.shouldfind");
        log.debug("Classes = {}", Arrays.toString(classes));
        final List<Class<?>> classList = new ArrayList<>();
        Collections.addAll(classList, classes);
        assertTrue(classList.contains(ThisShouldBeFound.class));
    }

    @Test
    public void classesForAPackageRecursive()
            throws IOException, ClassNotFoundException {
        final Class<?>[] classes = ClassHelper.getClasses("org.fluentjava");
        log.debug("Classes = {}", Arrays.toString(classes));
        final List<Class<?>> classList = new ArrayList<>();
        Collections.addAll(classList, classes);
        assertTrue(classList.contains(ThisShouldBeFound.class));
        assertTrue(classList.contains(ClassHelperTest.class));
    }

    @Test
    public void classesForAPackageContainingUtf8Characters()
            throws IOException, ClassNotFoundException {
        final Class<?>[] classes = ClassHelper
                .getClasses("org.fluentjava.völundr");
        log.debug("Classes = {}", Arrays.toString(classes));
        final List<Class<?>> classList = new ArrayList<>();
        Collections.addAll(classList, classes);
        assertTrue(classList.contains(ContainingUtf8Characters.class));
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
