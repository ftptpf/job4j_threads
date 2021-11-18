package ru.job4j.multithreading.synchronization.dynamic;

import net.jcip.annotations.ThreadSafe;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Коллекция которая будет корректно работать в многопоточной среде.
 * @param <T>
 */

@ThreadSafe
public class SingleLockList<T> implements Iterable<T> {
    private final List<T> list;

    public SingleLockList() {
    }

    public SingleLockList(List<T> list) {
        this.list = (List) list.clone();

    }

    public synchronized void add(T value) {
        list.add(value);
    }

    public synchronized T get(int index) {
        return list.get(index);
    }

    @Override
    public synchronized Iterator<T> iterator() {
        return copy(this.array).iterator();
    }
}
