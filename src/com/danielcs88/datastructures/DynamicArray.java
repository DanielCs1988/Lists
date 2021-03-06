package com.danielcs88.datastructures;

import java.util.*;
import java.util.stream.Collectors;

public class DynamicArray<E> implements List<E> {

    private static final int INITIAL_SIZE = 10;
    private static final int EXPANSION_MULTIPLIER = 2;

    private int size;
    private Object[] data;

    public DynamicArray() {
        data = new Object[INITIAL_SIZE];
    }

    public DynamicArray(Collection<E> collection) {
        Object[] temp = collection.toArray();
        size = temp.length;
        int size = this.size > INITIAL_SIZE ? this.size : INITIAL_SIZE;
        data = new Object[size];
        System.arraycopy(temp, 0, data, 0, this.size);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean contains(Object o) {
        for (Object elem : data) {
            if (elem.equals(o)) return true;
        }
        return false;
    }

    @Override
    public Iterator<E> iterator() {
        return new Iter();
    }

    @Override
    public Object[] toArray() {
        Object[] retArray = new Object[size];
        System.arraycopy(data, 0, retArray, 0, size);
        return retArray;
    }

    @SuppressWarnings({"unchecked", "SuspiciousSystemArraycopy"})
    @Override
    public <T1> T1[] toArray(T1[] a) {
        if (a.length < size) {
            return (T1[])Arrays.copyOf(data, size, a.getClass());
        }
        System.arraycopy(data, 0, a, 0, size);
        return a;
    }

    @Override
    public boolean add(E newElem) {
        resizeArrayIfNeeded();
        data[size++] = newElem;
        return true;
    }

    private void resizeArrayIfNeeded() {
        if (size == data.length) {
            Object[] tempBuffer = new Object[size * EXPANSION_MULTIPLIER];
            System.arraycopy(data, 0, tempBuffer, 0, size);
            data = tempBuffer;
        }
    }

    @Override
    public boolean remove(Object o) {
        for (int i = 0; i < size; i++) {
            if (data[i].equals(o)) {
                System.arraycopy(data, i + 1, data, i, size - i - 1);
                size--;
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        boolean found;
        for (Object o : c) {
            found = false;
            for (Object elem : data) {
                if (elem.equals(o)) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        Object[] buffer = data;
        int newPartSize = c.size();
        if (size + newPartSize > data.length) {
            buffer = new Object[size + newPartSize];
            System.arraycopy(data, 0, buffer, 0, size);
        }
        System.arraycopy(c.toArray(), 0, buffer, size, newPartSize);
        data = buffer;
        size += newPartSize;
        return true;
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        checkIfIndexValid(index);
        Object[] buffer = data;
        int newPartSize = c.size();
        if (size + newPartSize > data.length) {
            buffer = new Object[size + newPartSize];
            System.arraycopy(data, 0, buffer, 0, size);
        }
        System.arraycopy(buffer, index, buffer, index + newPartSize, size - index);
        System.arraycopy(c.toArray(), 0, buffer, index, newPartSize);
        data = buffer;
        size += newPartSize;
        return true;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        Set<?> checkList = new HashSet<>(c);
        data = this.stream().filter(elem -> !checkList.contains(elem)).toArray();
        size = data.length;
        return true;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        Set<?> checkList = new HashSet<>(c);
        data = this.stream().filter(checkList::contains).toArray();
        size = data.length;
        return true;
    }

    @Override
    public void clear() {
        // May want to add a complete purge of the old array to help with GC.
        data = new Object[INITIAL_SIZE];
        size = 0;
    }

    @Override
    public E get(int index) {
        checkIfIndexValid(index);
        // It IS safe, because the class itself is type-bound.
        return (E)data[index];
    }

    @Override
    public E set(int index, E element) {
        checkIfIndexValid(index);
        data[index] = element;
        return element;
    }

    @Override
    public void add(int index, E element) {
        checkIfIndexValid(index);
        resizeArrayIfNeeded();
        System.arraycopy(data, index, data, index + 1, size - index);
        data[index] = element;
        size++;
    }

    @Override
    public E remove(int index) {
        checkIfIndexValid(index);
        E temp = (E)data[index];
        System.arraycopy(data, index + 1, data, index, size - index - 1);
        size--;
        return temp;
    }

    @Override
    public int indexOf(Object o) {
        for (int i = 0; i < size; i++) {
            if (data[i].equals(o)) return i;
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        int index = -1;
        for (int i = 0; i < size; i++) {
            if (data[i].equals(o)) index = i;
        }
        return index;
    }

    @Override
    public ListIterator<E> listIterator() {
        return new ListIter();
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        return new ListIter(index);
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        checkIfIndexValid(fromIndex);
        checkIfIndexValid(toIndex);
        // May need extra check if from is greater than to.
        Object[] subArray = Arrays.copyOfRange(data, fromIndex, toIndex);
        return Arrays.asList((E[])subArray);
    }

    private void checkIfIndexValid(int index) {
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException();
    }

    @Override
    public String toString() {
        return this.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(", "));
    }

    private class Iter implements Iterator<E> {

        protected int index;

        @Override
        public boolean hasNext() {
            return index < size;
        }

        @Override
        public E next() {
            return get(index++);
        }

        @Override
        public void remove() {
            System.arraycopy(data, index + 1, data, index, size - index - 1);
            size--;

        }
    }

    private class ListIter extends Iter implements ListIterator<E> {

        ListIter(){}

        ListIter(int index) {
            this.index = index;
        }

        @Override
        public boolean hasPrevious() {
            return index > 0;
        }

        @Override
        public E previous() {
            return get(index++);
        }

        @Override
        public int nextIndex() {
            return index + 1;
        }

        @Override
        public int previousIndex() {
            return index - 1;
        }

        @Override
        public void set(E e) {
            data[index] = e;
        }

        @Override
        public void add(E e) {
            resizeArrayIfNeeded();
            System.arraycopy(data, index, data, index + 1, size - index);
            data[index] = e;
            size++;
        }
    }
}
