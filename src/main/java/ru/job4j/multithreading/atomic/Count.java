package ru.job4j.multithreading.atomic;

/**
 * Счетчик в котором для атомарности в методах используется синхронизация.
 */
public class Count {
    private int value;

    public synchronized void increment() {
        value++;
    }

    public synchronized int get() {
        return value;
    }
}
