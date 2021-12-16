package ru.job4j.multithreading.pools.completablefuture.sample;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * Считаем сумму элементов по диагоналям матрицы.
 * Сколько диагоналей в матрице? 2 * N. Каждая сумма может быть подсчитана независимо от другой.
 * Если выполнить этот код по сравнению с последовательной версией кода,
 * то можно увидеть, что он работает от 1.5 до 2 раз быстрее.
 * Стоит обратить внимание, что в цикле мы запускаем две задачи.
 * Одну с начала обхода, другую с конца. Так распределение идет более равномерно.
 */
public class MatrixDiagonal {
    public static int[] asyncSum(int[][] matrix) throws ExecutionException, InterruptedException {
        int n = matrix.length;
        int[] sums = new int[2 * n];
        Map<Integer, CompletableFuture<Integer>> futures = new HashMap<>();
        /*считаем сумму по главной диагонали*/
        futures.put(0, getTask(matrix, 0, n - 1, n - 1));
       /* считаем суммы по побочным диагоналям*/
        for (int k = 1; k <= n; k++) {
            futures.put(k, getTask(matrix, 0, k - 1, k - 1));
            if (k < n) {
                futures.put(2 * n - k, getTask(matrix, n - k, n - 1, n - 1));
            }
        }
        for (Integer key : futures.keySet()) {
            sums[key] = futures.get(key).get();
        }
        return sums;
    }

    private static CompletableFuture<Integer> getTask(int[][] data, int startRow, int endRow, int startCol) {
        return CompletableFuture.supplyAsync(
                () -> {
                    int sum = 0;
                    int col = startCol;
                    for (int i = startRow; i <= endRow; i++) {
                        sum += data[i][col];
                        col--;
                    }
                    return sum;
                }
        );
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        int[][] matrix = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        int[] result = asyncSum(matrix);
        System.out.println(Arrays.toString(result));
    }
}
