package com.danielcs88.datastructures;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class DynamicArrayTest extends DoublyLinkedListTest {

    @BeforeEach
    private void fillTheList() {
        list = new DynamicArray<>(Arrays.asList("a", "b", "c", "d", "e", "f", "g", "h", "i", "j"));
    }

    @Test
    void testInsertAll() {
        list.addAll(4, Arrays.asList("z", "q", "x"));
        String expected = "a, b, c, d, z, q, x, e, f, g, h, i, j";
        assertEquals(expected, list.toString());
    }
}