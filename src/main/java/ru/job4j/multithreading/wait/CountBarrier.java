package ru.job4j.multithreading.wait;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

/**
 * Класс блокирует выполнение по условию счетчика.
 * Переменная total содержит количество вызовов метода count().
 * Метод count изменяет состояние программы.
 *
 */
@ThreadSafe
public class CountBarrier {
    @GuardedBy("monitor")
    private final Object monitor = this;
    private final int total;
    private int count = 0;

    public CountBarrier(final int total) {
        this.total = total;
    }

    public void count() {
        synchronized (monitor) {
            count++;
            System.out.println("Count " + count + " " + Thread.currentThread().getName() + " " + Thread.currentThread().getState());
            monitor.notifyAll();
        }
    }

    public void await() {
        synchronized (monitor) {
            while (count < total) {
                try {
                    monitor.wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            System.out.println(Thread.currentThread().getName() + " " + Thread.currentThread().getState());
        }
    }

    public static void main(String[] args) throws InterruptedException {
        CountBarrier countBarrier = new CountBarrier(4);
        Thread first = new Thread(
                () -> {
                    System.out.println(Thread.currentThread().getName() + " started");
                    countBarrier.count();
                    countBarrier.await();
                }, "First"
        );
        Thread second = new Thread(
                () -> {
                    System.out.println(Thread.currentThread().getName() + " started");
                    countBarrier.count();
                    countBarrier.await();
                }, "Second"
        );
        Thread third = new Thread(
                () -> {
                    System.out.println(Thread.currentThread().getName() + " started");
                    countBarrier.count();
                    countBarrier.await();
                }, "Third"
        );

        first.start();
        second.start();
        third.start();

    }
}
