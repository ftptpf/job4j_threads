package ru.job4j.multithreading.pools.threadpool;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;
import ru.job4j.multithreading.wait.producer.SimpleBlockingQueue;

import java.util.LinkedList;
import java.util.List;

/**
 * Реализация thread пула, в котором можно переиспользовать Threads.
 * work (Runnable job) - этот метод добавляет задачи в блокирующую очередь tasks
 * shutdown() - этот метод завершает все запущенные задачи
 */
@ThreadSafe
public class ThreadPool {
    @GuardedBy("this")
    private final List<Thread> threads = new LinkedList<>();
    private final SimpleBlockingQueue<Runnable> task;
    private boolean isStopped = false;

    public ThreadPool(SimpleBlockingQueue<Runnable> task) {
        this.task = task;
        int size = Runtime.getRuntime().availableProcessors();
        while (!isStopped) {

        }
        for (int i = 0; i < size; i++) {
            Thread thread = new Thread(
                    () -> {
                        try {
                            task.poll().run();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
            );
            threads.add(thread);
        }

        for (Thread thread : threads) {
            thread.start();
        }
    }

/*    public ThreadPool() {
        int size = Runtime.getRuntime().availableProcessors();
        for (int i = 0; i < size; i++) {
            Thread thread = new Thread(
                    () -> {
                        try {
                            task.poll().run();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
            );
            threads.add(thread);
        }

        for (Thread thread : threads) {
            thread.start();
        }
    }*/

    public void work(Runnable job) throws InterruptedException {
        if (isStopped) {
            throw new IllegalStateException("ThreadPool is stopped");
        }
        task.offer(job);
    }

    public void shutdown() {
        isStopped = true;
        for (Thread thread : threads) {
            thread.interrupt();
        }
    }

    @Override
    public String toString() {
        return "ThreadPool{"
                + "threads=" + threads
                + ", task=" + task
                + ", isStopped=" + isStopped
                + '}';
    }
}
