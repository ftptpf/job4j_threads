package ru.job4j.multithreading.synchronization.dynamic;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Коллекция которая корректно работает в многопоточной среде.
 * @param <T>
 */

@ThreadSafe
public class SingleLockList<T> implements Iterable<T> {
    @GuardedBy("this")
    private final List<T> list;

    public SingleLockList() {
        list = new ArrayList<>();
    }

    public SingleLockList(List<T> list) {
        this.list = cloneList(list);
    }

    public synchronized void add(T value) {
        list.add(value);
    }

    public synchronized T get(int index) {
        return list.get(index);
    }

    @Override
    public synchronized Iterator<T> iterator() {
        return cloneList(list).iterator();
    }

    public synchronized List<T> cloneList(List<T> tList) {
        return new ArrayList<>(tList);
    }
}
