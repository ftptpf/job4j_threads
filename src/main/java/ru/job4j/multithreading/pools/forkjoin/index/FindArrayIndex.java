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
 * Если объекта нет в массиве - выводим отрицательно значение индекса.
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
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                System.out.println("Value: " + array[i] + " index: " + i);
                if (array[i].equals(value)) {
                    return i;
                }
            }
        } else {
            int mid = size / 2;
            FindArrayIndex<V> left = new FindArrayIndex<V>(Arrays.copyOfRange(array, 0, mid), value);
            FindArrayIndex<V> right = new FindArrayIndex<V>(Arrays.copyOfRange(array, mid, size), value);
            int l = left.invoke();
            int r = right.invoke();
            System.out.println("size: " + size + " mid: " + mid + " left: " + l + " right: " + r);
            if (l >= 0) {
                return l;
            }
            if (r >= 0) {
                return r + mid;
            }
            return l + r;
        }
        return -1;
    }

    public static void main(String[] args) {
        ForkJoinPool forkJoinPool = ForkJoinPool.commonPool();
        Integer[] array = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23};
        int result = forkJoinPool.invoke(new FindArrayIndex<>(array, 22));
        System.out.println(result);
    }
}
