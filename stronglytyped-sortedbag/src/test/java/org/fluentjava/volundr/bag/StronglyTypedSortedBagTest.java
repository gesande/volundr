package org.fluentjava.volundr.bag;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.concurrent.TimeUnit;

import org.awaitility.Awaitility;
import org.junit.jupiter.api.Test;

public class StronglyTypedSortedBagTest {

    @Test
    public void empty() {
        final StronglyTypedSortedBag<String> bag = StronglyTypedSortedBag
                .treeBag();
        assertEquals(0, bag.size());
        assertEquals(0, bag.uniqueSamples().size());
        assertTrue(bag.isEmpty());
        assertNull(bag.findFirst());
        assertNull(bag.findLast());
    }

    @Test
    public void add() {
        final StronglyTypedSortedBag<String> bag = StronglyTypedSortedBag
                .treeBag();
        assertTrue(bag.add("value1"));
        assertEquals(1, bag.uniqueSamples().size());
        assertEquals(1, bag.count("value1"));
        assertFalse(bag.isEmpty());
    }

    @Test
    public void testAddUniqueness() {
        final StronglyTypedSortedBag<String> bag = StronglyTypedSortedBag
                .treeBag();
        assertTrue(bag.add("value1"));
        assertFalse(bag.add("value1"));
        assertEquals(1, bag.uniqueSamples().size());
        assertEquals(2, bag.count("value1"));
        assertFalse(bag.isEmpty());
    }

    @Test
    public void findFirst() {
        final StronglyTypedSortedBag<String> bag = StronglyTypedSortedBag
                .treeBag();
        bag.add("value1");
        bag.add("value2");
        assertEquals(2, bag.size());
        assertEquals(2, bag.uniqueSamples().size());
        assertEquals(1, bag.count("value1"));
        assertEquals(1, bag.count("value2"));
        final String findFirst = bag.findFirst();
        assertNotNull(findFirst);
        assertEquals("value1", findFirst);
    }

    @Test
    public void findLast() {
        final StronglyTypedSortedBag<String> bag = StronglyTypedSortedBag
                .treeBag();
        bag.add("value1");
        bag.add("value2");
        assertEquals(2, bag.size());
        assertEquals(2, bag.uniqueSamples().size());
        assertEquals(1, bag.count("value1"));
        assertEquals(1, bag.count("value2"));
        final String findLast = bag.findLast();
        assertNotNull(findLast);
        assertEquals("value2", findLast);
    }

    @Test
    public void firstBeingLast() {
        final StronglyTypedSortedBag<String> bag = StronglyTypedSortedBag
                .treeBag();
        bag.add("value1");
        assertEquals(1, bag.size());
        assertEquals(1, bag.uniqueSamples().size());
        assertEquals(1, bag.count("value1"));
        final String findFirst = bag.findFirst();
        assertNotNull(findFirst);
        assertEquals("value1", findFirst);
        final String findLast = bag.findLast();
        assertNotNull(findLast);
        assertEquals("value1", findLast);
    }

    @Test
    public void multiThreadUseOfBag() {
        final StronglyTypedSortedBag<String> bag = StronglyTypedSortedBag
                .synchronizedTreeBag();
        final Runnable runnable1 = () -> bag.add("value1");
        final Runnable runnable2 = () -> bag.add("value2");
        new Thread(runnable1).start();
        new Thread(runnable2).start();
        Awaitility.await().pollDelay(100, TimeUnit.MILLISECONDS)
                .atMost(1000, TimeUnit.MILLISECONDS)
                .until(() -> bag.uniqueSamples().size() == 2);
        assertEquals(1, bag.count("value1"));
        assertEquals(1, bag.count("value2"));

        final String findFirst = bag.findFirst();
        assertNotNull(findFirst);
        assertEquals("value1", findFirst);

        final String findLast = bag.findLast();
        assertNotNull(findLast);
        assertEquals("value2", findLast);
    }

    @Test
    public void size() {
        final StronglyTypedSortedBag<String> bag = StronglyTypedSortedBag
                .treeBag();
        assertEquals(0, bag.size());
        bag.add("value1");
        bag.add("value1");
        bag.add("value1");
        bag.add("value1");
        bag.add("value2");
        bag.add("value1");
        bag.add("value2");
        bag.add("value1");
        bag.add("value3");
        bag.add("value1");
        assertEquals(10, bag.size());
        assertEquals(3, bag.uniqueSamples().size());
    }

    @Test
    public void clear() {
        final StronglyTypedSortedBag<String> bag = StronglyTypedSortedBag
                .treeBag();
        assertEquals(0, bag.size());
        bag.add("value1");
        bag.add("value2");
        bag.add("value3");
        assertEquals(3, bag.size());
        assertEquals(3, bag.uniqueSamples().size());
        bag.clear();
        assertEquals(0, bag.size());
        assertEquals(0, bag.uniqueSamples().size());
    }

}
