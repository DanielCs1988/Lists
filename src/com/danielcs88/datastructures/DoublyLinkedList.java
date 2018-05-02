package com.danielcs88.datastructures;

import java.util.*;

public class DoublyLinkedList<E> implements List<E> {

    private Link<E> firstElement;
    private int size;

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
        return new Object[0];
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return null;
    }

    @Override
    public boolean add(E e) {
        link(e, null, firstElement);
        return true;
    }

    @Override
    public boolean remove(Object o) {
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        return false;
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return false;
    }

    @Override
    public void clear() {
        Iterator<E> iter = iterator();
        while (iter.hasNext()) {
            iter.next();
            iter.remove();
        }
        firstElement = null;
    }

    @Override
    public E get(int index) {
        ListIterator<E> iter = setIteratorToIndex(index - 1);
        return iter.next();
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
        return null;
    }

    private ListIter setIteratorToIndex(int index) {
        checkIfIndexValid(index);
        ListIter iter = new ListIter();
        for (int i = 0; i < index; i++) {
            iter.next();
        }
        return iter;
    }

    private void checkIfIndexValid(int index) {
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException();
    }

    private E unlink(Link<E> link) {
        if (link.prev == null) {
            firstElement = link.next;
        } else {
            link.prev.next = link.next;
        }
        link.next.prev = link.prev;

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
            firstElement = newLink;
        } else {
            prevLink.next = newLink;
        }
        nextLink.prev = newLink;
        size ++;
        return newLink;
    }

    private class Iter implements Iterator<E> {

        Link<E> next;
        Link<E> current;

        Iter() {
            next = firstElement;
        }

        @Override
        public boolean hasNext() {
            return next != null;
        }

        @Override
        public E next() {
            current = next;
            next = next.next;
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
            return current != null && current.prev != null;
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
            current = link(elem, current, next);
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

        public Link(T self, Link<T> prev, Link<T> next) {
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
