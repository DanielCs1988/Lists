package com.danielcs88.datastructures;

import java.util.*;
import java.util.stream.Collectors;

public class SingleLinkList <T> implements List<T> {

    private Link<T> firstElement;
    private int size;

    SingleLinkList(Iterable<T> iterable) {
        for (T elem : iterable) {
            add(elem);
        }
    }

    SingleLinkList() {}

    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean contains(Object o) {
        for (T elem : this) {
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

    public ArrayList<T> toList() {
        return new ArrayList<>(this);
    }

    public T getFirst() {
        return firstElement.self;
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

    public T get(int index) {
        ListIterator<T> iter = setIteratorToIndex(index - 1);
        return iter.next();
    }

    @Override
    public T set(int index, T element) {
        checkIfIndexValid(index);
        ListIterator<T> iter = setIteratorToIndex(index);
        iter.set(element);
        return element;
    }

    @Override
    public boolean add(T t) {
        link(t, null, firstElement);
        return true;
    }

    public void add(int index, T elem) {
        checkIfIndexValid(index);
        ListIterator<T> iter = setIteratorToIndex(index);
        iter.add(elem);
    }

    public T removeFirst() {
        return unlink(firstElement, null);
    }

    public T remove(int index) {
        checkIfIndexValid(index);
        ListIter iter = setIteratorToIndex(index);
        return iter.pop();
    }

    @Override
    public int indexOf(Object o) {
        int counter = 0;
        for (T elem : this) {
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
        for (T elem : this) {
            if (elem.equals(o)) {
                lastIndex = counter;
            }
            counter ++;
        }
        return lastIndex;
    }

    @Override
    public ListIterator<T> listIterator() {
        return new ListIter();
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        checkIfIndexValid(index);
        return setIteratorToIndex(index);
    }

    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        checkIfIndexValid(fromIndex);
        checkIfIndexValid(toIndex);
        List<T> subList = new ArrayList<>();
        ListIterator<T> iter = setIteratorToIndex(fromIndex);

        for (int i = 0; i < toIndex - fromIndex; i++) {
            subList.add(iter.next());
        }
        return subList;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iter();
    }

    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        return null;
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
    public boolean addAll(Collection<? extends T> c) {
        return false;
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
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
        Iterator<T> iter = iterator();
        while (iter.hasNext()) {
            iter.next();
            iter.remove();
        }
        firstElement = null;
    }

    private T unlink(Link<T> linkToRemove, Link<T> prevLink) {
        if (prevLink == null) {
            firstElement = firstElement.next;
        } else {
            prevLink.next = linkToRemove.next;
        }
        T retVal = linkToRemove.self;
        linkToRemove.next = null;
        linkToRemove.self = null;
        size --;
        return retVal;
    }

    private void link(T newElem, Link<T> prevLink, Link<T> nextLink) {
        Link<T> newLink = new Link<>(newElem);
        if (prevLink != null) {
            prevLink.next = newLink;
        } else {
            firstElement = newLink;
        }
        newLink.next = nextLink;
        size ++;
    }

    private class Iter implements Iterator<T> {

        protected Link<T> next;
        protected Link<T> current;
        protected Link<T> prev;

        Iter() {
            next = firstElement;
        }

        @Override
        public boolean hasNext() {
            return next != null;
        }

        @Override
        public T next() {
            prev = current;
            current = next;
            next = next.next;
            return current.self;
        }

        @Override
        public void remove() {
            if (current == null) throw new NoSuchElementException();
            unlink(current, prev);
            current = next;
        }
    }

    private class ListIter extends Iter implements ListIterator<T> {

        private int index;

        @Override
        public T next() {
            T retVal = super.next();
            index ++;
            return retVal;
        }

        @Override
        public boolean hasPrevious() {
            return false;
        }

        @Override
        public T previous() {
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
        public void set(T t) {
            if (current == null) throw new NoSuchElementException();
            current.self = t;
        }

        @Override
        public void add(T elem) {
            if (current == null) throw new NoSuchElementException();
            link(elem, prev, current);
        }

        public T pop() {
            if (current == null) throw new NoSuchElementException();
            T retVal = unlink(current, prev);
            current = next;
            return retVal;
        }
    }

    private final class Link <X> {

        private Link<X> next;
        private X self;

        Link(X element) {
            self = element;
        }

        Link(X element, Link<X> next) {
            self = element;
            this.next = next;
        }

        @Override
        public String toString() {
            return self.toString();
        }
    }
}
