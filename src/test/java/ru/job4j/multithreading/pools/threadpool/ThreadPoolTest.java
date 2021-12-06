package ru.job4j.multithreading.pools.threadpool;

import org.junit.Test;
import ru.job4j.multithreading.wait.producer.SimpleBlockingQueue;

import java.util.stream.IntStream;

import static org.junit.Assert.*;

public class ThreadPoolTest {



    @Test
    public void test() throws InterruptedException {
        ThreadPool threadPool = new ThreadPool();
        Producer producer = new Producer();
        threadPool.work(producer);
        SimpleBlockingQueue<Runnable> queue = new SimpleBlockingQueue<>(5);

        Thread thread = new Thread(
                () -> {
                    for (int i = 0; i < 20; i++) {
                        try {
                            queue.offer(new Producer());
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
                }
        );
        thread.start();





    }

    private class Producer implements Runnable {

        @Override
        public void run() {
            int size = Runtime.getRuntime().availableProcessors();
            System.out.print(Thread.currentThread().getName() + " " + IntStream.range(0, size));
            System.out.println();
        }
    }
}

