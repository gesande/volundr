package net.sf.völundr;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClassHelperTest {
    private final static Logger LOG = LoggerFactory
            .getLogger(ClassHelperTest.class);

    @Ignore
    @Test
    public void classesForAPackage()
            throws IOException, ClassNotFoundException {
        final Class<?>[] classes = ClassHelper.getClasses("net.sf.völundr");
        LOG.debug("Classes = {}", Arrays.toString(classes));
        final List<Class<?>> classList = new ArrayList<>();
        for (final Class<?> clazz : classes) {
            classList.add(clazz);
        }
        // assertEquals(2, classList.size());
        assertTrue(classList.contains(ClassHelper.class));
        assertTrue(classList.contains(ClassHelperTest.class));
    }

    @Test
    public void noClassesForAnUnknownSubPackage()
            throws IOException, ClassNotFoundException {
        assertEquals(0, ClassHelper.getClasses("net.sf.unknown").length);
    }

    @Test
    public void noClassesForAnUnknownPackage()
            throws IOException, ClassNotFoundException {
        assertEquals(0, ClassHelper.getClasses("fail.i.have").length);
    }
}
