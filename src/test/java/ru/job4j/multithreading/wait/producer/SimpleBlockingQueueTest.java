package ru.job4j.multithreading.wait.producer;

import org.junit.Test;

import static org.junit.Assert.*;

public class SimpleBlockingQueueTest {

    @Test
    public void testThreads() throws InterruptedException {
        final SimpleBlockingQueue simpleBlockingQueue = new SimpleBlockingQueue(5);
        Thread producer = new Thread(
                () -> {
                    for (int i = 0; i < 10; i++) {
                        simpleBlockingQueue.offer(i);
                        System.out.println(i);
                    }
                }, "Producer"
        );
        Thread customer = new Thread(
                () -> {
                    for (int i = 0; i < 10; i++) {
                        System.out.println(simpleBlockingQueue.poll());
                    }
                }, "Customer"
        );
        producer.start();
        customer.start();
    }
}