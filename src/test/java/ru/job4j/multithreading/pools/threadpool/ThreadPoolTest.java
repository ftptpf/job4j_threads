package ru.job4j.multithreading.pools.threadpool;

import org.junit.Test;
import ru.job4j.multithreading.wait.producer.SimpleBlockingQueue;

import java.util.stream.IntStream;

import static org.junit.Assert.*;

public class ThreadPoolTest {



    @Test
    public void test() throws InterruptedException {
        SimpleBlockingQueue<Runnable> queue = new SimpleBlockingQueue<>(5);
        ThreadPool threadPool = new ThreadPool(queue);

        Thread threadSubmitTask = new Thread(
                () -> {
                    for (int i = 0; i < 20; i++) {
                        try {
                            threadPool.work(new Producer());
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
                }
        );
        threadSubmitTask.start();
        Thread.sleep(1000);
        threadPool.shutdown();





    }

    private class Producer implements Runnable {

        @Override
        public void run() {
            int size = Runtime.getRuntime().availableProcessors();
            System.out.print(Thread.currentThread().getName() + " well done !");
            System.out.println();
        }
    }
}

