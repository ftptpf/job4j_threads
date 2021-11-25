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
        final List<Integer> list = new ArrayList<>();
        Thread producer = new Thread(
                () -> {
                    for (int i = 0; i < 10; i++) {
                        queue.offer(i);
                    }
                }, "Producer"
        );
        Thread customer = new Thread(
                () -> {
                    for (int i = 0; i < 10; i++) {
                        list.add(queue.poll());
                    }
                }, "Customer"
        );
        producer.start();
        assertThat(queue.toString(), is("SimpleBlockingQueue{queue=[], limit=5}"));
        producer.join(5000);
        customer.start();
        customer.join(5000);
        assertThat(queue.toString(), is("SimpleBlockingQueue{queue=[], limit=5}"));
        assertThat(list.toString(), is("list"));
        producer.join();
        assertThat(queue.toString(), is("[0, 1, 2, 3, 4, 5, 6, 7, 8, 9]"));
    }
}