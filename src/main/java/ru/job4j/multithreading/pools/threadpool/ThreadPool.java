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
    private final int size = Runtime.getRuntime().availableProcessors();
    private boolean isStopped = false;

    public ThreadPool(SimpleBlockingQueue<Runnable> task) {
        this.task = task;
        for (int i = 0; i < size; i++) {
            threads.add(new Thread((Runnable) task));
/*            Thread thread = new Thread(
                    () -> {
                        try {
                            Runnable runnable = task.poll();
                            runnable.run();
                            System.out.println("Take job " + runnable);

                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
            );
            threads.add(thread);*/
            System.out.println(Thread.currentThread().getName() + " add");
        }

        threads.forEach(Thread::start);
    }

    public synchronized void work(Runnable job) throws InterruptedException {
        if (isStopped) {
            throw new IllegalStateException("ThreadPool is stopped");
        }
        task.offer(job);
        // System.out.println(Thread.currentThread().getName() + " add job" + job);
    }

    public synchronized void shutdown() {
        isStopped = true;
        for (Thread thread : threads) {
            thread.interrupt();
            System.out.println(Thread.currentThread().getName() + " interrupted");
        }
    }

    public synchronized void waitUntilAllTasksFinished() {
        while (!task.isEmpty()) {
            try {
                Thread.sleep(100);
                System.out.println(Thread.currentThread().getName() + " sleep 100 мс");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
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

    public static void main(String[] args) throws InterruptedException {
        SimpleBlockingQueue<Runnable> queue = new SimpleBlockingQueue<>(5);
        ThreadPool threadPool = new ThreadPool(queue);

        for (int i = 0; i < 10; i++) {
            int jobNumber = i;
            threadPool.work(
                    () -> {
                        System.out.println(Thread.currentThread().getName() + " make job № " + jobNumber);
                        try {
                            Thread.sleep(0);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
            );
        }
        //threadPool.waitUntilAllTasksFinished();
        threadPool.shutdown();

    }
}
