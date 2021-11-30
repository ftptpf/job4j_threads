package ru.job4j.multithreading.nonblocking.count;

import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.atomic.AtomicReference;

@ThreadSafe
public class CASCount {
    private final AtomicReference<Integer> count = new AtomicReference<>();

    public CASCount() {
        this.count.set(0);
    }

    public void increment() {
        Integer value;
        do {
            value = get();
        } while (!count.compareAndSet(value, value + 1));
    }

    public Integer get() {
        return count.get();
    }
}
