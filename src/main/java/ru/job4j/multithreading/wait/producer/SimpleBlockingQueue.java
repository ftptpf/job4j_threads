package ru.job4j.multithreading.wait.producer;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Блокирующая очередь ограниченная по размеру.
 * Если очередь заполнена полностью, то при попытке добавления поток Producer блокируется,
 * до тех пор пока Consumer не извлечет очередные данные, т.е. в очереди появится свободное место.
 * И наоборот если очередь пуста поток Consumer блокируется,
 * до тех пор пока Producer не поместит в очередь данные.
 * @param <T>
 */
@ThreadSafe
public class SimpleBlockingQueue<T> {
    @GuardedBy("this")
    private final Queue<T> queue = new LinkedList<>();
    private final int limit;

    public SimpleBlockingQueue(int limit) {
        this.limit = limit;
    }

    public synchronized void offer(T value) {
        while (queue.size() == limit) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        if (queue.size() == 0) {
            this.notifyAll();
        }
        queue.add(value);
    }

    public synchronized T poll() {
        while (queue.size() == 0) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        if (queue.size() == limit) {
            this.notifyAll();
        }
        return queue.remove();
    }

    @Override
    public synchronized String toString() {
        return "SimpleBlockingQueue{"
                + "queue=" + queue
                + ", limit=" + limit
                + '}';
    }
}
