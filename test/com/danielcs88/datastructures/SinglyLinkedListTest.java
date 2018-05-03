package com.danielcs88.datastructures;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SinglyLinkedListTest {

    List<String> list;

    @BeforeEach
    private void fillTheList() {
        list = new SinglyLinkedList<>(Arrays.asList("j", "i", "h", "g", "f", "e", "d", "c", "b", "a"));
    }

    @Test
    void testSize() {
        assertEquals(10, list.size());
    }

    @Test
    void testIsEmpty() {
        list.clear();
        assertTrue(list.isEmpty());
    }

    @Test
    void testContains() {
        assertTrue(list.contains("f"));
    }

    @Test
    void testToString() {
        String expected = "a, b, c, d, e, f, g, h, i, j";
        assertEquals(expected, list.toString());
    }

    @Test
    void testGet() {
        assertEquals("d", list.get(3));
    }

    @Test
    void testSet() {
        list.set(0, "x");
        assertEquals("x", list.get(0));
    }

    @Test
    void testAdd() {
        list.add("r");
        list.add("q");
        String expected = "q, r, a, b, c, d, e, f, g, h, i, j";
        assertEquals(expected, list.toString());
    }

    @Test
    void testInsert() {
        list.add(7, "x");
        String expected = "a, b, c, d, e, f, g, h, x, i, j";
        assertEquals(expected, list.toString());
    }

    @Test
    void testRemoveByObject() {
        list.remove("e");
        String expected = "a, b, c, d, f, g, h, i, j";
        assertEquals(expected, list.toString());
    }

    @Test
    void testRemoveByIndex() {
        list.remove(5);
        String expected = "a, b, c, d, e, g, h, i, j";
        assertEquals(expected, list.toString());
    }


    @Test
    void testIndexOf() {
        assertEquals(4, list.indexOf("e"));
    }

    @Test
    void testLastIndexOf() {
        list.add("e");
        list.add("e");
        assertEquals(6, list.lastIndexOf("e"));
    }

    @Test
    void testListIterator() {
        assertNotNull(list.listIterator());
    }

    @Test
    void testListIteratorToIndex() {
        assertNotNull(list.listIterator(5));
    }

    @Test
    void testSubList() {
        List<String> sublist = list.subList(3, 6);
        assertEquals("[d, e, f]", sublist.toString());
    }

    @Test
    void testIterator() {
        assertNotNull(list.iterator());
    }

    @Test
    void testToArray() {
        String expected = "[a, b, c, d, e, f, g, h, i, j]";
        assertEquals(expected, Arrays.toString(list.toArray()));
    }

    @Test
    void testToTypedArray() {
        String expected = "[a, b, c, d, e, f, g, h, i, j]";
        String[] arr = list.toArray(new String[0]);
        assertEquals(expected, Arrays.toString(arr));
    }

    @Test
    void testContainsAll() {
        assertTrue(list.containsAll(Arrays.asList("a", "f", "h")));
    }

    @Test
    void testAddAll() {
        list.addAll(Arrays.asList("z", "q", "x"));
        String expected = "x, q, z, a, b, c, d, e, f, g, h, i, j";
        assertEquals(expected, list.toString());
    }

    @Test
    void testInsertAll() {
        list.addAll(4, Arrays.asList("z", "q", "x"));
        String expected = "a, b, c, d, e, z, q, x, f, g, h, i, j";
        assertEquals(expected, list.toString());
    }

    @Test
    void testRemoveAll() {
        list.removeAll(Arrays.asList("a", "c", "f", "j"));
        String expected = "b, d, e, g, h, i";
        assertEquals(expected, list.toString());
    }

    @Test
    void testRetainAll() {
        list.retainAll(Arrays.asList("a", "c", "f"));
        String expected = "a, c, f";
        assertEquals(expected, list.toString());
    }

    @Test
    void testClear() {
        list.clear();
        assertTrue(list.isEmpty());
    }
}