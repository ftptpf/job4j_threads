package ru.job4j.multithreading.pools.forkjoin.index;

import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/**
 * Поиск индекса в массиве уникальных объектов.
 * Если размер массива не больше 10, используем обычный линейный поиск,
 * в ином случае параллельный поиск. Метод поиска обобщенный.
 * Если объекта нет в массиве - выводим отрицательно значение индекса.
 */
@ThreadSafe
public class FindArrayIndex<V> extends RecursiveTask<Integer> {
    private final V[] array;
    private final V value;
    private final int from;
    private final int to;

    public FindArrayIndex(V[] array, V value) {
        this.array = array;
        this.value = value;
        this.from = 0;
        this.to = array.length - 1;
    }

    public FindArrayIndex(V[] array, V value, int from, int to) {
        this.array = array;
        this.value = value;
        this.from = from;
        this.to = to;
    }

    @Override
    protected Integer compute() {
        int size = to - from;
        if (size <= 10) {
            for (int i = from; i <= to; i++) {
                if (array[i].equals(value)) {
                    return i;
                }
            }
        } else {
            int mid = (from + to) / 2;
            FindArrayIndex<V> left = new FindArrayIndex<>(array, value, from, mid);
            FindArrayIndex<V> right = new FindArrayIndex<>(array, value, mid + 1, to);
            int l = left.invoke();
            int r = right.invoke();
            return Math.max(r, l);
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
