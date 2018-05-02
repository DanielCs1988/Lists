package com.danielcs88.datastructures;

import java.util.*;
import java.util.stream.Collectors;

public class SinglyLinkedList<E> implements List<E> {

    private Link<E> firstElement;
    private int size;

    SinglyLinkedList(Collection<? extends E> collection) {
        addAll(collection);
    }

    SinglyLinkedList() {}

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
    public String toString() {
        return this.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(", "));
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
    public boolean add(E e) {
        link(e, null, firstElement);
        return true;
    }

    public void add(int index, E element) {
        ListIterator<E> iter = setIteratorToIndex(index);
        iter.add(element);
    }

    public E remove(int index) {
        // TODO: FIX INDEX
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
        checkIfIndexValid(toIndex);
        ListIterator<E> iter = setIteratorToIndex(fromIndex);
        List<E> subList = new ArrayList<>();

        for (int i = 0; i < toIndex - fromIndex; i++) {
            subList.add(iter.next());
        }
        return subList;
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
    public <T1> T1[] toArray(T1[] a) {
        // TODO
        return null;
    }

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
        Iterator<E> iter = iterator();
        boolean removed = false;
        while (iter.hasNext()) {
            if (checkList.contains(iter.next())) {
                iter.remove();
                removed = true;
            }
        }
        return removed;
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
        firstElement = null;
    }

    private E unlink(Link<E> linkToRemove, Link<E> prevLink) {
        if (prevLink == null) {
            firstElement = firstElement.next;
        } else {
            prevLink.next = linkToRemove.next;
        }
        E retVal = linkToRemove.self;
        linkToRemove.next = null;
        linkToRemove.self = null;
        size --;
        return retVal;
    }

    private Link<E> link(E newElem, Link<E> prevLink, Link<E> nextLink) {
        Link<E> newLink = new Link<>(newElem);
        if (prevLink == null) {
            firstElement = newLink;
        } else {
            prevLink.next = newLink;
        }
        newLink.next = nextLink;
        size ++;
        return newLink;
    }

    private class Iter implements Iterator<E> {

        Link<E> next;
        Link<E> current;
        Link<E> prev;

        Iter() {
            next = firstElement;
        }

        @Override
        public boolean hasNext() {
            return next != null;
        }

        @Override
        public E next() {
            prev = current;
            current = next;
            next = next.next;
            return current.self;
        }

        @Override
        public void remove() {
            if (current == null) throw new NoSuchElementException();
            unlink(current, prev);
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
            return false;
        }

        @Override
        public E previous() {
            return null;
        }

        @Override
        public int nextIndex() {
            return index;
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
            prev = current;
            current = link(elem, current, next);
        }

        public E pop() {
            if (current == null) throw new NoSuchElementException();
            E retVal = unlink(current, prev);
            current = next;
            return retVal;
        }
    }

    private final class Link <T> {

        private Link<T> next;
        private T self;

        Link(T element) {
            self = element;
        }

        Link(T element, Link<T> next) {
            self = element;
            this.next = next;
        }

        @Override
        public String toString() {
            return self.toString();
        }
    }
}
