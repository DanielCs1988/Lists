package com.danielcs88.datastructures;

import java.util.*;
import java.util.stream.Collectors;

public class DoublyLinkedList<E> implements List<E> {

    private Link<E> head;
    private Link<E> tail;
    private int size;

    public DoublyLinkedList(Collection<? extends E> collection) {
        addAll(collection);
    }

    public DoublyLinkedList() {
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
        for (E elem : this) {
            if (elem.equals(o)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Iterator<E> iterator() {
        return new Iter();
    }

    @Override
    public Object[] toArray() {
        Object[] arr = new Object[size];
        int index = 0;
        for (E elem : this) {
            arr[index++] = elem;
        }
        return arr;
    }

    @Override
    @SuppressWarnings({"unchecked", "SuspiciousSystemArraycopy"})
    public <T1> T1[] toArray(T1[] a) {
        Object[] buffer = toArray();
        if (a.length < size) {
            return (T1[])Arrays.copyOf(buffer, size, a.getClass());
        }
        System.arraycopy(buffer, 0, a, 0, size);
        return a;
    }

    @Override
    public boolean add(E e) {
        link(e, null, head);
        return true;
    }

    @Override
    public boolean remove(Object o) {
        Iterator<E> iter = iterator();
        while (iter.hasNext()) {
            if (iter.next().equals(o)) {
                iter.remove();
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
            for (Object elem : this) {
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
        for (E elem : c) {
            add(elem);
        }
        return true;
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        ListIterator<E> iter = setIteratorToIndex(index);
        for (E elem : c) {
            iter.add(elem);
        }
        return true;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        Set<?> checkList = new HashSet<>(c);
        // Alternative to the implementation used in singly linked list.
        return removeIf(checkList::contains);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        Set<?> checkList = new HashSet<>(c);
        return removeIf(elem -> !checkList.contains(elem));
    }

    @Override
    public void clear() {
        Iterator<E> iter = iterator();
        while (iter.hasNext()) {
            iter.next();
            iter.remove();
        }
        head = null;
    }

    @Override
    public E get(int index) {
        return index == 0 ? tail.self : setIteratorToIndex(index).current.self;
    }

    @Override
    public E set(int index, E element) {
        ListIterator<E> iter = setIteratorToIndex(index);
        iter.set(element);
        return element;
    }

    @Override
    public void add(int index, E element) {
        ListIterator<E> iter = setIteratorToIndex(index);
        iter.add(element);
    }

    @Override
    public E remove(int index) {
        ListIter iter = setIteratorToIndex(index);
        return iter.pop();
    }

    @Override
    public int indexOf(Object o) {
        int counter = 0;
        for (E elem : this) {
            if (elem.equals(o)) {
                return counter;
            }
            counter ++;
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        int counter = 0;
        int lastIndex = -1;
        for (E elem : this) {
            if (elem.equals(o)) {
                lastIndex = counter;
            }
            counter ++;
        }
        return lastIndex;
    }

    @Override
    public ListIterator<E> listIterator() {
        return new ListIter();
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        return setIteratorToIndex(index);
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        ListIter iter = setIteratorToIndex(fromIndex);
        List<E> subList = new ArrayList<>();

        for (int i = 0; i < toIndex - fromIndex; i++) {
            subList.add(iter.current.self);
            iter.next();
        }
        return subList;
    }

    private ListIter setIteratorToIndex(int index) {
        checkIfIndexValid(index);
        ListIter iter = new ListIter();
        for (int i = 0; i <= index; i++) {
            iter.next();
        }
        return iter;
    }

    @Override
    public String toString() {
        return this.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(", "));
    }

    private void checkIfIndexValid(int index) {
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException();
    }

    private E unlink(Link<E> link) {
        if (link.prev == null) {
            head = link.next;
        } else {
            link.prev.next = link.next;
        }
        if (link.next == null) {
            tail = link.prev;
        } else {
            link.next.prev = link.prev;
        }

        E retVal = link.self;
        link.next = null;
        link.prev = null;
        link.self = null;
        size --;
        return retVal;
    }

    private Link<E> link(E newElem, Link<E> prevLink, Link<E> nextLink) {
        Link<E> newLink = new Link<>(newElem, prevLink, nextLink);
        if (prevLink == null) {
            head = newLink;
        } else {
            prevLink.next = newLink;
        }
        if (nextLink == null) {
            tail = newLink;
        } else {
            nextLink.prev = newLink;
        }
        size ++;
        return newLink;
    }

    private class Iter implements Iterator<E> {

        Link<E> next;
        Link<E> current;

        Iter() {
            next = tail;
        }

        @Override
        public boolean hasNext() {
            return next != null;
        }

        @Override
        public E next() {
            current = next;
            next = next.prev;
            return current.self;
        }

        @Override
        public void remove() {
            if (current == null) throw new NoSuchElementException();
            unlink(current);
            current = null;
        }
    }

    private class ListIter extends Iter implements ListIterator<E> {

        private int index;

        @Override
        public E next() {
            E retVal = super.next();
            index ++;
            return retVal;
        }

        @Override
        public boolean hasPrevious() {
            return current != null && current.next != null;
        }

        @Override
        public E previous() {
            // TODO
            return null;
        }

        @Override
        public int nextIndex() {
            return index < size - 1 ? index + 1 : -1;
        }

        @Override
        public int previousIndex() {
            return index - 1;
        }

        @Override
        public void set(E e) {
            if (current == null) throw new NoSuchElementException();
            current.self = e;
        }

        @Override
        public void add(E elem) {
            if (current == null) throw new NoSuchElementException();
            current = link(elem, current, current.next);
        }

        public E pop() {
            if (current == null) throw new NoSuchElementException();
            E retVal = unlink(current);
            current = null;
            return retVal;
        }
    }

    private final class Link <T> {

        private Link<T> next;
        private Link<T> prev;
        private T self;

        Link(T element) {
            self = element;
        }

        Link(T self, Link<T> prev, Link<T> next) {
            this.self = self;
            this.prev = prev;
            this.next = next;
        }

        @Override
        public String toString() {
            return self.toString();
        }
    }
}
