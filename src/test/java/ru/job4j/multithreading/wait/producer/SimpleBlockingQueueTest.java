package ru.job4j.multithreading.wait.producer;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

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
}
