package com.danielcs88.datastructures;

import java.util.*;

public class Main {

    public static void main(String... args) {
        testLinkedList();
        //testSequenceList();
    }

    private static void testSequenceList() {
        List<Integer> testList = new DynamicArray<>();
        for (int i = 0; i < 10; i++) {
            testList.add(i);
        }
        System.out.println(testList);
        System.out.println(testList.containsAll(new ArrayList<Integer>(Arrays.asList(1, 2, 73))));
        Integer[] testNumArr = testList.toArray(new Integer[15]);
        System.out.println(Arrays.toString(testNumArr));
    }

    private static void testLinkedList() {
        List<Integer> testList = new SinglyLinkedList<>(Arrays.asList(
                1, 2, 3, 4, 5, 6, 7, 8, 9, 10
        ));

        System.out.println(testList);

        testList.clear();
        testList.add(1);
        testList.add(2);
        System.out.println(testList);


    }

}
