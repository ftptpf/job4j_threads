package ru.job4j.multithreading.pools.threadpool;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;
import ru.job4j.multithreading.wait.producer.SimpleBlockingQueue;

import java.util.LinkedList;
import java.util.List;

/**
 * Реализация thread пула.
 */
@ThreadSafe
public class ThreadPool {
    @GuardedBy("this")
    private final List<Thread> threads = new LinkedList<>();
    private final SimpleBlockingQueue<Runnable> task;

    public ThreadPool(SimpleBlockingQueue<Runnable> task) {
        this.task = task;
        int size = Runtime.getRuntime().availableProcessors();
        System.out.println("Size is " + size);
        for (int i = 0; i < size; i++) {
            Thread thread = new Thread(
                    () -> {
                        while (!Thread.currentThread().isInterrupted()) {
                            try {
                                task.poll().run();

                            } catch (InterruptedException e) {
                                Thread.currentThread().interrupt();
                            }
                        }
                    }
            );
            threads.add(thread);
            System.out.println("Add " + Thread.currentThread().getName() + " thread to ThreadPool");
        }
        threads.forEach(Thread::start);
    }

    /**
     * Добавляет задачи в блокирующую очередь.
     * @param job выполняемые задачи
     * @throws InterruptedException
     */
    public synchronized void work(Runnable job) throws InterruptedException {
        task.offer(job);
    }

    /**
     * Завершаем все запущенные Thread в ThreadPool .
     */
    public synchronized void shutdown() {
        for (Thread thread : threads) {
            thread.interrupt();
            System.out.println(Thread.currentThread().getName() + " thread interrupted");
        }
    }

    /**
     * Ожидаем когда опустеет очередь задач.
     */
    public synchronized void waitUntilAllTasksFinished() {
        while (!task.isEmpty()) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        SimpleBlockingQueue<Runnable> queue = new SimpleBlockingQueue<>(5);
        ThreadPool threadPool = new ThreadPool(queue);

        for (int i = 0; i < 30; i++) {
            int jobNumber = i;
            threadPool.work(
                    () -> {
                        System.out.println(Thread.currentThread().getName() + " make job № " + jobNumber);
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
            );
        }
        threadPool.waitUntilAllTasksFinished();
        threadPool.shutdown();
    }
}
