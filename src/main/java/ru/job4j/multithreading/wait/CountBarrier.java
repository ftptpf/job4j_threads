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
            monitor.notifyAll();
        }
    }

    public void await() {
        synchronized (monitor) {
            System.out.println("Count is " + count);
            while (count >= total) {
                System.out.println("Count is " + count + " " + Thread.currentThread().getName() + "Do the job");
                count = 0;
            }
            try {
                monitor.wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        CountBarrier countBarrier = new CountBarrier(4);
/*        Thread first = new Thread(
                () -> {
                    System.out.println(Thread.currentThread().getName() + " started");
                    countBarrier.await();
                }, "First"
        );
        Thread second = new Thread(
                () -> {
                    System.out.println(Thread.currentThread().getName() + " started");
                    countBarrier.await();
                }, "Second"
        );
        Thread third = new Thread(
                () -> {
                    System.out.println(Thread.currentThread().getName() + " started");
                    countBarrier.await();
                }, "Third"
        );*/

/*        first.start();
        second.start();
        third.start();*/
        for (int i = 0; i < 5; i++) {
            countBarrier.count();
            new Thread(
                    () -> {
                        System.out.println(Thread.currentThread().getName() + " started");
                        countBarrier.await();
                    }
            );
        }

    }
}
