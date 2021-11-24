package ru.job4j.multithreading.wait;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

/**
 * Класс блокирует выполнение по условию счетчика.
 * counterThread - увеличивает счетчик
 * jobThread - выполняет работу
 * Пока параметр счетчика будет (count < total),
 * jobThread будет находиться в состоянии ожидания (wait),
 * послед выполнения условия - jobThread выполнит свою работу (выведет сообщение не экран).
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
            System.out.println("Count is " + count + ",  " + Thread.currentThread().getName() + " - increase counter");
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
            System.out.println("Count is " + count + " " + Thread.currentThread().getName() + " Do job after wait");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        CountBarrier countBarrier = new CountBarrier(4);
        Thread counterThread = new Thread(
                () -> {
                    System.out.println(Thread.currentThread().getName() + " Started");
                    for (int i = 0; i < 50; i++) {
                        countBarrier.count();
                    }
                }, "Counter Thread"
        );
        Thread jobThread
                = new Thread(
                () -> {
                    countBarrier.await();
                    System.out.println(Thread.currentThread().getName() + " Started");
                }, "Job Thread"
        );

        jobThread.start();
        counterThread.start();
    }
}
