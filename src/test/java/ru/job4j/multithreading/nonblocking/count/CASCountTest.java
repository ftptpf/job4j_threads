package ru.job4j.multithreading.nonblocking.count;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

public class CASCountTest {

    @Test
    public void increment() throws InterruptedException {
        int steps = 60;
        CASCount counter = new CASCount();

        Thread thread1 = new Thread(
                () -> {
                    for (int i = 0; i < steps; i++) {
                        counter.increment();
                    }
                }
        );

        Thread thread2 = new Thread(
                () -> {
                    for (int i = 0; i < steps; i++) {
                        counter.increment();
                    }
                }
        );
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
        assertThat(counter.get(), is(steps * 2));
    }

    @Test
    public void moreThen100() throws InterruptedException {
        int steps = 120;
        CASCount counter = new CASCount();

        Thread thread1 = new Thread(
                () -> {
                    for (int i = 0; i < steps; i++) {
                        counter.increment();
                        System.out.println(Thread.currentThread().getName() + " " + counter.get());
                    }
                }
        );

        Thread thread2 = new Thread(
                () -> {
                    for (int i = 0; i < steps; i++) {
                        counter.increment();
                        System.out.println(Thread.currentThread().getName() + " " + counter.get());
                    }
                }
        );
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
        assertThat(counter.get(), is(steps * 2));
    }

    @Test
    public void oneMainThread220() {
        int steps = 220;
        CASCount counter = new CASCount();
        for (int i = 0; i < steps; i++) {
            counter.increment();
            System.out.println(Thread.currentThread().getName() + " " + counter.get());
        }
        assertThat(counter.get(), is(steps));
    }
}
