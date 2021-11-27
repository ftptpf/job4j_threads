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

    public synchronized void offer(T value) throws InterruptedException {
        while (queue.size() == limit) {
            this.wait();
        }
        queue.add(value);
        this.notifyAll();
    }

    public synchronized T poll() throws InterruptedException {
        while (queue.size() == 0) {
            this.wait();
        }
        T item = queue.remove();
        this.notifyAll();
        return item;
    }

    public synchronized boolean isEmpty() {
        return queue.isEmpty();
    }

    @Override
    public synchronized String toString() {
        return "SimpleBlockingQueue{"
                + "queue=" + queue
                + ", limit=" + limit
                + '}';
    }
}
