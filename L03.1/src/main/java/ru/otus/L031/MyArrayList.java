package ru.otus.L031;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

public class MyArrayList<T> implements List<T> {

    private Object[] INTERNAL_STORAGE;
    private final int INITIAL_CAPACITY = 10;
    private int SIZE;

    public MyArrayList() {
        INTERNAL_STORAGE = new Object[INITIAL_CAPACITY];
        this.SIZE = 0;
    }

    public int size() {
        return SIZE;
    }


    public boolean isEmpty() {
        return SIZE == 0;
    }

    public boolean contains(Object o) {
        if (!isEmpty()) {
            for (int i = 0; i < SIZE; i++) {
                if (o.equals(INTERNAL_STORAGE[i])) return true;
            }
        }
        return false;
    }


    public Iterator<T> iterator() {
        Iterator<T> iterator = new Iterator<T>() {
            private int currentPosition = -1;

            @Override
            public boolean hasNext() {
                boolean result = currentPosition < (SIZE - 2);
                if (!result) currentPosition = -1;
                return result;
            }

            @Override
            public T next() {
                currentPosition++;
                return (T) INTERNAL_STORAGE[currentPosition];
            }

            @Override
            public void remove() {
                System.arraycopy(INTERNAL_STORAGE, (currentPosition + 1), INTERNAL_STORAGE, currentPosition, SIZE - currentPosition + 1);
                SIZE--;
            }
        };
        return iterator;
    }

    public ListIterator<T> listIterator() {
        ListIterator listIterator = new ListIterator() {
            private int currentPosition = -1;

            @Override
            public boolean hasNext() {
                boolean result = currentPosition < (SIZE - 2);
                if (!result) currentPosition = -1; //If we reach the end of Array
                return result;
            }

            @Override
            public Object next() {
                currentPosition++;
                return INTERNAL_STORAGE[currentPosition];
            }

            @Override
            public boolean hasPrevious() {
                return currentPosition > -1;
            }

            @Override
            public Object previous() {
                return INTERNAL_STORAGE[currentPosition];
            }

            @Override
            public int nextIndex() {
                int nextIndex = currentPosition + 2;
                if(nextIndex >= SIZE) throw new IndexOutOfBoundsException("Next index is out of range, breaking the top bound of List");
                return nextIndex;
            }

            @Override
            public int previousIndex() {
                int prevIndex = currentPosition + 1;
                if(prevIndex < 0) throw new IndexOutOfBoundsException("Next index is out of range, breaking the bottom bound of List");
                return 0;
            }

            @Override
            public void remove() {
                System.arraycopy(INTERNAL_STORAGE, (currentPosition + 1), INTERNAL_STORAGE, currentPosition, SIZE - currentPosition + 1);
                SIZE--;
            }

            @Override
            public void set(Object o) {
                INTERNAL_STORAGE[currentPosition + 1] = o;
            }

            @Override
            public void add(Object o) {

            }
        };
        return listIterator;
    }

    public ListIterator<T> listIterator(int index) {
        return null;
    }
    //TODO forEach

    public void forEach(Consumer<? super T> action) {

    }

    public Object[] toArray() {
        return Arrays.copyOf(INTERNAL_STORAGE, SIZE);
    }
    //TODO public <T1> T1[] toArray(T1[] a){}

    public <T1> T1[] toArray(T1[] a) {
        return null;
    }
    //TODO public <T1> T1[] toArray(T1[] a){}

    public <T1> T1[] toArray(IntFunction<T1[]> generator) {
        return null;
    }
    //TODO add increase()

    public boolean add(T t) {
        INTERNAL_STORAGE[SIZE] = t;
        SIZE++;
        return true;
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

    public boolean containsAll(Collection<?> c) {
        return false;
    }

    public void add(int index, T element) {
        if ((SIZE + 1) >= INTERNAL_STORAGE.length) increase((int) (INTERNAL_STORAGE.length * 1.3));
        Object[] tail = Arrays.copyOfRange(INTERNAL_STORAGE, index, SIZE);
        System.arraycopy(INTERNAL_STORAGE, index, tail, 0, (SIZE - index - 1));
        INTERNAL_STORAGE[index] = element;
        System.arraycopy(tail, 0, INTERNAL_STORAGE, index + 1, tail.length);
        SIZE++;
    }

    public boolean addAll(Collection<? extends T> c) {
        /*If size of the added collection is bigger then free space in INTERNAL_STORAGE*/
        if (c.size() > (INITIAL_CAPACITY - SIZE)) increase(c.size());
        System.arraycopy(c.toArray(), 0, INTERNAL_STORAGE, SIZE, c.size());
        SIZE = SIZE + c.size();
        return true;
    }

    public boolean addAll(int index, Collection<? extends T> c) {
        return false;
    }

    public boolean removeAll(Collection<?> c) {
        return false;
    }

    public boolean removeIf(Predicate<? super T> filter) {
        return false;
    }

    public boolean retainAll(Collection<?> c) {
        return false;
    }

    public void replaceAll(UnaryOperator<T> operator) {

    }


    public void sort() {
        Arrays.sort(INTERNAL_STORAGE, 0, SIZE);
    }

    public void clear() {
        Arrays.fill(INTERNAL_STORAGE, 0, SIZE - 1, null);
        SIZE = 0;
    }

    public T get(int index) {
        return (T) INTERNAL_STORAGE[index];
    }

    public T set(int index, T element) {
        T temp = (T) INTERNAL_STORAGE[index];
        INTERNAL_STORAGE[index] = element;
        return temp;
    }

    public T remove(int index) {
        return null;
    }

    public int indexOf(Object o) {
        return 0;
    }

    public int lastIndexOf(Object o) {
        return 0;
    }

    public List<T> subList(int fromIndex, int toIndex) {
        return null;
    }

    public Spliterator<T> spliterator() {
        return null;
    }


    public Stream<T> parallelStream() {
        return null;
    }

    public void random() {
        Object temp;
        Random rnd = new Random();
        for (int i = 1; i <= SIZE; i++) {
            int index0 = rnd.nextInt(SIZE);
            int index1 = rnd.nextInt(SIZE);
            temp = INTERNAL_STORAGE[index0];
            INTERNAL_STORAGE[index0] = INTERNAL_STORAGE[index1];
            INTERNAL_STORAGE[index1] = temp;
        }
    }

    public String toString() {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < SIZE; i++) {
            result.append(INTERNAL_STORAGE[i].toString()).append(", ");
        }
        return result.toString();
    }

    private void increase(int addSize) {
        int newSize = (int) ((SIZE + addSize) * 1.3);
        INTERNAL_STORAGE = Arrays.copyOf(INTERNAL_STORAGE, newSize);
    }
}
