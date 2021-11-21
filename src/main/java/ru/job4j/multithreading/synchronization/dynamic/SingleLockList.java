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
        this.list = new SingleLockList<T>(list).cloneList();
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
        return cloneList().iterator();

    }

    public List<T> cloneList() {
        List<T> listClone = new ArrayList<>();
        synchronized (this) {
            for (int i = 0; i < list.size(); i++) {
                listClone.add(i, list.get(i));
            }
        }
        return listClone;
    }
}
