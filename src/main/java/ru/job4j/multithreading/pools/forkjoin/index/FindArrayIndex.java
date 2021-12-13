package ru.job4j.multithreading.pools.forkjoin.index;

import net.jcip.annotations.ThreadSafe;

import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/**
 * Поиск индекса в массиве объектов.
 * Если размер массива не больше 10, используем обычный линейный поиск,
 * в ином случае параллельный поиск.
 * Метод поиска обобщенный.
 */
@ThreadSafe
public class FindArrayIndex<V> extends RecursiveTask<Integer> {
    private final V[] array;
    private final V value;

    public FindArrayIndex(V[] array, V value) {
        this.array = array;
        this.value = value;
    }

    @Override
    protected Integer compute() {
        System.out.println("Current thread: " + Thread.currentThread());
        int size = array.length;
        if (size <= 10) {
            for (int i = 0; i < size; i++) {
                System.out.println("Value: " + array[i] + " index: " + i);
                if (array[i].equals(value)) {
                    return i;
                }
            }
        } else {
            int mid = size / 2;
            FindArrayIndex<V> left = new FindArrayIndex<V>(Arrays.copyOfRange(array, 0, mid), value);
            FindArrayIndex<V> right = new FindArrayIndex<V>(Arrays.copyOfRange(array, mid + 1, size - 1), value);
            left.fork();
            right.fork();
            int l = left.join();
            int r = right.join();
            System.out.println("size: " + size + " mid: " + mid + " left: " + l + " right: " + r + " Max: " + Math.max(l, r));
            if (l >= 0) {
                return l;
            }
            if (r >= 0) {
                return r + l;
            }
            return -1;
        }
        return -1;
    }

    public static void main(String[] args) {
        ForkJoinPool forkJoinPool = ForkJoinPool.commonPool();
        Integer[] array = {2, 3, 5, 6, 8, 99, 23, 21, 45, 121, 12, 12, 56, 32, 11, 4, 58, 23, 90, 6, 77, 88, 21};
        int result = forkJoinPool.invoke(new FindArrayIndex<>(array, 77));
        System.out.println(result);
    }
}
