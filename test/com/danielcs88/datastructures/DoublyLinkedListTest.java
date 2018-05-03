package com.danielcs88.datastructures;

import org.junit.jupiter.api.BeforeEach;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class DoublyLinkedListTest extends SinglyLinkedListTest {

    @BeforeEach
    private void fillTheList() {
        list = new DoublyLinkedList<>(Arrays.asList("j", "i", "h", "g", "f", "e", "d", "c", "b", "a"));
    }
}