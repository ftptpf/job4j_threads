package ru.job4j.multithreading.pools.threadpool;

import org.junit.Test;

import java.util.stream.IntStream;

import static org.junit.Assert.*;

public class ThreadPoolTest {



    @Test
    public void test() throws InterruptedException {
        ThreadPool threadPool = new ThreadPool();
        Generator generator = new Generator();
        threadPool.work(generator);

    }

    private class Generator implements Runnable {

        @Override
        public void run() {
            int size = Runtime.getRuntime().availableProcessors();
            System.out.println(IntStream.range(0, size));
        }
    }
}

