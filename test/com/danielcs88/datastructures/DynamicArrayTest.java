package com.danielcs88.datastructures;

import org.junit.jupiter.api.BeforeEach;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class DynamicArrayTest extends SinglyLinkedListTest {

    @BeforeEach
    private void fillTheList() {
        list = new DynamicArray<>(Arrays.asList("a", "b", "c", "d", "e", "f", "g", "h", "i", "j"));
    }
}