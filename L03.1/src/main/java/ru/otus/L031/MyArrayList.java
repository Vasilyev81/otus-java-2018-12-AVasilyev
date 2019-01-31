package ru.otus.L031;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

public class MyArrayList<T> implements List<T> {

    private Object[] internalStorage;
    private static final int INITIAL_CAPACITY = 10;
    private int size;


    public MyArrayList() {
        internalStorage = new Object[INITIAL_CAPACITY];
        this.size = 0;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean contains(Object o) {
        if (!isEmpty()) {
            for (int i = 0; i < size; i++) {
                if (o.equals(internalStorage[i])) return true;
            }
        }
        return false;
    }

    public Iterator<T> iterator() {
        Iterator<T> iterator = new Iterator<T>() {
            private int currentPosition = -1;

            @Override
            public boolean hasNext() {
                boolean result = currentPosition < (size - 2);
                if (!result) currentPosition = -1;
                return result;
            }

            @Override
            public T next() {
                currentPosition++;
                return (T) internalStorage[currentPosition];
            }

            @Override
            public void remove() {
                System.arraycopy(internalStorage, (currentPosition + 1), internalStorage, currentPosition, size - currentPosition + 1);
                size--;
            }
        };
        return iterator;
    }

    public ListIterator<T> listIterator() {
        ListIterator listIterator = new ListIterator() {
            private int currentPosition = -1;

            @Override
            public boolean hasNext() {
                boolean result = currentPosition < (size - 2);
                if (!result) currentPosition = -1; //If we reach the end of Array
                return result;
            }

            @Override
            public Object next() {
                currentPosition++;
                return internalStorage[currentPosition];
            }

            @Override
            public boolean hasPrevious() {
                return currentPosition > -1;
            }

            @Override
            public Object previous() {
                return internalStorage[currentPosition];
            }

            @Override
            public int nextIndex() {
                int nextIndex = currentPosition + 2;
                if (nextIndex >= size)
                    throw new IndexOutOfBoundsException("Next index is out of range, breaking the top bound of List");
                return nextIndex;
            }

            @Override
            public int previousIndex() {
                int prevIndex = currentPosition + 1;
                if (prevIndex < 0)
                    throw new IndexOutOfBoundsException("Next index is out of range, breaking the bottom bound of List");
                return 0;
            }

            @Override
            public void remove() {
                System.arraycopy(internalStorage, (currentPosition + 1), internalStorage, currentPosition, size - currentPosition + 1);
                size--;
            }

            @Override
            public void set(Object o) {
                internalStorage[currentPosition + 1] = o;
            }

            //TODO Implement method
            @Override
            public void add(Object o) {

            }
        };
        return listIterator;
    }

    public Object[] toArray() {
        return Arrays.copyOf(internalStorage, size);
    }

    public boolean add(T t) {
        if ((size + 1) >= internalStorage.length) increase((int) (internalStorage.length * 1.3));
        internalStorage[size] = t;
        size++;
        return true;
    }

    public void add(int index, T element) {
        checkIndexForOutOfBounds(index);
        if ((size + 1) >= internalStorage.length) increase((int) (internalStorage.length * 1.3));
        if (index == size) this.add(element);
        else {
            Object[] tail = Arrays.copyOfRange(internalStorage, index, size);
            System.arraycopy(internalStorage, index, tail, 0, (size - index - 1));
            internalStorage[index] = element;
            System.arraycopy(tail, 0, internalStorage, index + 1, tail.length);
            size++;
        }
    }

    public boolean addAll(Collection<? extends T> c) {
        if (c.size() > (INITIAL_CAPACITY - size)) increase(c.size());
        System.arraycopy(c.toArray(), 0, internalStorage, size, c.size());
        size = size + c.size();
        return true;
    }

    public void sort() {
        Arrays.sort(internalStorage, 0, size);
    }

    public void clear() {
        Arrays.fill(internalStorage, 0, size - 1, null);
        size = 0;
    }

    public T get(int index) {
        checkIndexForOutOfBounds(index);
        return (T) internalStorage[index];
    }

    public T set(int index, T element) {
        checkIndexForOutOfBounds(index);
        T temp = (T) internalStorage[index];
        internalStorage[index] = element;
        return temp;
    }

    public boolean remove(Object o) {
        boolean result = false;
        while (iterator().hasNext()) {
            if (iterator().next().equals(o)) {
                result = true;
                iterator().remove();
            }
        }
        return result;
    }

    public T remove(int index) {
        checkIndexForOutOfBounds(index);
        T result = (T) internalStorage[index];
        System.arraycopy(internalStorage, (index + 1), internalStorage, index, (size - index));
        size--;
        return result;
    }

    private void checkIndexForOutOfBounds(int index) {
        if (index < 0 || index > size)
            throw new IndexOutOfBoundsException("Index is out of Array bounds, index: " + index);
    }

    public void random() {
        Object temp;
        Random rnd = new Random();
        for (int i = 1; i <= size; i++) {
            int index0 = rnd.nextInt(size);
            int index1 = rnd.nextInt(size);
            temp = internalStorage[index0];
            internalStorage[index0] = internalStorage[index1];
            internalStorage[index1] = temp;
        }
    }

    private void increase(int addSize) {
        int newSize = (int) ((size + addSize) * 1.3);
        internalStorage = Arrays.copyOf(internalStorage, newSize);
    }

    public String toString() {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < size; i++) {
            result.append(internalStorage[i].toString()).append(", ");
        }
        return result.toString();
    }


    /**
     * Section of
     * not-implemented
     * methods
     */


    public boolean containsAll(Collection<?> c) {
        throw new UnsupportedOperationException("This method is not implemented");
    }

    public void forEach(Consumer<? super T> action) {
        throw new UnsupportedOperationException("This method is not implemented");
    }

    public <T1> T1[] toArray(IntFunction<T1[]> generator) {
        throw new UnsupportedOperationException("This method is not implemented");
    }

    public boolean addAll(int index, Collection<? extends T> c) {
        checkIndexForOutOfBounds(index);
        throw new UnsupportedOperationException("This method is not implemented");
    }

    public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException("This method is not implemented");
    }

    public boolean removeIf(Predicate<? super T> filter) {
        throw new UnsupportedOperationException("This method is not implemented");
    }

    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException("This method is not implemented");
    }

    public void replaceAll(UnaryOperator<T> operator) {
        throw new UnsupportedOperationException("This method is not implemented");
    }

    public ListIterator<T> listIterator(int index) {
        throw new UnsupportedOperationException("This method is not implemented");
    }

    public <T1> T1[] toArray(T1[] a) {
        throw new UnsupportedOperationException("This method is not implemented");
    }

    public int indexOf(Object o) {
        throw new UnsupportedOperationException("This method is not implemented");
    }

    public int lastIndexOf(Object o) {
        throw new UnsupportedOperationException("This method is not implemented");
    }

    public List<T> subList(int fromIndex, int toIndex) {
        checkIndexForOutOfBounds(fromIndex);
        checkIndexForOutOfBounds(toIndex);
        throw new UnsupportedOperationException("This method is not implemented");
    }

    public Spliterator<T> spliterator() {
        throw new UnsupportedOperationException("This method is not implemented");
    }


    public Stream<T> parallelStream() {
        throw new UnsupportedOperationException("This method is not implemented");
    }
}
