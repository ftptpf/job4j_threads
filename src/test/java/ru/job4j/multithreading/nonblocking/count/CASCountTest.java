package ru.job4j.multithreading.nonblocking.count;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

public class CASCountTest {

    @Test
    public void increment() throws InterruptedException {
        int steps = 100;
        CASCount counter = new CASCount();

        Thread thread1 = new Thread(
                () -> {
                    for (int i = 0; i < steps; i++) {
                        counter.increment();
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
                }
        );
        thread1.start();
        Thread thread2 = new Thread(
                () -> {
                    for (int i = 0; i < steps; i++) {
                        counter.increment();
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
                }
        );

        thread2.start();
        assertThat(counter.get(), is(steps * 2));
    }
}