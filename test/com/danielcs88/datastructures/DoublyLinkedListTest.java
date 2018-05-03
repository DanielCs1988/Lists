package com.danielcs88.datastructures;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class DoublyLinkedListTest extends SinglyLinkedListTest {

    @BeforeEach
    private void fillTheList() {
        list = new DoublyLinkedList<>(Arrays.asList("a", "b", "c", "d", "e", "f", "g", "h", "i", "j"));
    }

    @Test
    void testAdd() {
        list.add("k");
        list.add("l");
        String expected = "a, b, c, d, e, f, g, h, i, j, k, l";
        assertEquals(expected, list.toString());
    }

    @Test
    void testInsert() {
        list.add(7, "x");
        String expected = "a, b, c, d, e, f, g, x, h, i, j";
        assertEquals(expected, list.toString());
    }

    @Test
    void testLastIndexOf() {
        list.add("e");
        list.add("e");
        assertEquals(11, list.lastIndexOf("e"));
    }

    @Test
    void testAddAll() {
        list.addAll(Arrays.asList("z", "q", "x"));
        String expected = "a, b, c, d, e, f, g, h, i, j, z, q, x";
        assertEquals(expected, list.toString());
    }

    @Test
    void testInsertAll() {
        list.addAll(4, Arrays.asList("z", "q", "x"));
        String expected = "a, b, c, d, x, q, z, e, f, g, h, i, j";
        assertEquals(expected, list.toString());
    }
}