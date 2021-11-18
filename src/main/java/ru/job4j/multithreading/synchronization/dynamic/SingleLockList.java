package ru.job4j.multithreading.synchronization.dynamic;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.Iterator;
import java.util.List;

/**
 * Коллекция которая будет корректно работать в многопоточной среде.
 * @param <T>
 */

@ThreadSafe
public class SingleLockList<T> implements Iterable<T> {
    @GuardedBy("this")
    private final List<T> list;

    public SingleLockList() {
    }

    public SingleLockList(List<T> list) {
        this.list = list.clone();

    }

    public void add(T value) {
        synchronized (this) {
            list.add(value);
        }
    }

    public T get(int index) {
        synchronized (this) {
            return list.get(index);
        }
    }

    @Override
    public synchronized Iterator<T> iterator() {
        return copy(this.array).iterator();
    }

    @Override
    public List<T> clone() throws CloneNotSupportedException {
        return (List<T>) super.clone();
    }
}
