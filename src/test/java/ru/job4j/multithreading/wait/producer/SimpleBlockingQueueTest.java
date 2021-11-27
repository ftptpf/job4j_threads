package ru.job4j.multithreading.wait.producer;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

public class SimpleBlockingQueueTest {

    @Test
    public void testThreads() throws InterruptedException {
        final SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(5);
        List<Integer> start = List.of(0, 1, 2, 3, 4, 5, 6, 7, 8, 9);
        List<Integer> finish = new ArrayList<>();
        Thread producer = new Thread(
                () -> {
                    for (int i = 0; i < 10; i++) {
                        try {
                            queue.offer(i);
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
                }, "Producer"
        );
        Thread customer = new Thread(
                () -> {
                    for (int i = 0; i < 10; i++) {
                        try {
                            finish.add(queue.poll());
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
                }, "Customer"
        );
        producer.start();
        producer.join(10000);
        assertThat(queue.toString(), is("SimpleBlockingQueue{queue=[0, 1, 2, 3, 4], limit=5}"));
        customer.start();
        customer.join();
        assertThat(queue.toString(), is("SimpleBlockingQueue{queue=[], limit=5}"));
        assertThat(start.toString(), is(finish.toString()));
    }

    @Test
    public void whenFetchAllThenGetIt() throws InterruptedException {
        final CopyOnWriteArrayList<Integer> buffer = new CopyOnWriteArrayList<>();
        final SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(10);
        Thread producer = new Thread(
                () -> {
                    for (int i = 0; i < 5; i++) {
                        try {
                            queue.offer(i);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
                }
        );
        producer.start();
        Thread customer = new Thread(
                () -> {
                    while (!queue.isEmpty() || !Thread.currentThread().isInterrupted()) {
                        try {
                            buffer.add(queue.poll());
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
                }
        );
        customer.start();
        producer.join();
        customer.interrupt();
        customer.join();
        assertThat(buffer, is(Arrays.asList(0, 1, 2, 3, 4)));
    }
}
