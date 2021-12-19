package ru.job4j.multithreading.pools.completablefuture.sample;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * Считаем сумму элементов по диагоналям матрицы.
 * В матрице 2 * N диагоналей. Каждая сумма может быть подсчитана независимо от другой.
 * Если выполнить этот код по сравнению с последовательной версией кода,
 * то можно увидеть, что он работает от 1.5 до 2 раз быстрее.
 * Стоит обратить внимание, что в цикле мы запускаем две задачи.
 * Одну с начала обхода, другую с конца. Так распределение идет более равномерно.
 */
public class MatrixDiagonal {

    /**
     * Асинхронный расчет по диагоналям квадратной матрицы.
     * @param matrix двухмерный массив
     * @return массив сумм значений по диагонали
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public static int[] asyncSum(int[][] matrix) throws ExecutionException, InterruptedException {
        int n = matrix.length;
        int[] sums = new int[2 * n];
        Map<Integer, CompletableFuture<Integer>> futures = new HashMap<>();
        /* считаем сумму по главной диагонали, с верхнего левого угла в нижний правый*/
        futures.put(0, getTaskBottomLRight(matrix, 0, n - 1, 0));
       /* считаем суммы по побочным диагоналям,
       после for идем с верхнего левого угла в нижний правый и доходим до большой диагонали включительно
       после if идем с обратной стороны и доходим до центральной диагонали */
        for (int k = 1; k <= n; k++) {
            futures.put(k, getTaskBottomLeft(matrix, 0, k - 1, k - 1));
            if (k < n) {
                futures.put(2 * n - k, getTaskBottomLeft(matrix, n - k, n - 1, n - 1));
            }
        }
        /* перекладываем значения из HashMap в массив*/
        for (Integer key : futures.keySet()) {
            sums[key] = futures.get(key).get();
        }
        return sums;
    }

    /**
     * Асинхронный расчет суммы по ОДНОЙ диагонали квадратной матрицы
     * с верхнего правого угла в нижний левый.
     * Происходит смещение по диагонали с приращение от начальной строки к конечной
     * и с убыванием от крайнего столбца к начальному.
     * Например если startRow = 0, endRow = 2, startCol = 2 то мы будем двигаться
     * [0][2] -> [1][1] -> [2][0]
     * т.е .будем двигаться от правого ВЕРХНЕГО угла к левому НИЖНЕМУ.
     * @param data двухмерный массив
     * @param startRow индекс начальной строки
     * @param endRow индекс конечной строки
     * @param startCol индекс начальной колонки
     * @return результат асинхронного выполнения
     */
    private static CompletableFuture<Integer> getTaskBottomLeft(int[][] data, int startRow, int endRow, int startCol) {
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

    /**
     * Асинхронный расчет суммы по ОДНОЙ диагонали квадратной матрицы
     * с верхнего левого угла в нижний правому.
     * Происходит смещение по диагонали с приращение от начальной строки к конечной
     * и с убыванием от крайнего столбца к начальному.
     * Например если startRow = 0, endRow = 2, startCol = 2 то мы будем двигаться
     * [0][2] -> [1][1] -> [2][0]
     * т.е .будем двигаться от правого ВЕРХНЕГО угла к левому НИЖНЕМУ.
     * @param data двухмерный массив
     * @param startRow индекс начальной строки
     * @param endRow индекс конечной строки
     * @param startCol индекс начальной колонки
     * @return результат асинхронного выполнения
     */
    private static CompletableFuture<Integer> getTaskBottomLRight(int[][] data, int startRow, int endRow, int startCol) {
        return CompletableFuture.supplyAsync(
                () -> {
                    int sum = 0;
                    int col = startCol;
                    for (int i = startRow; i <= endRow; i++) {
                        sum += data[i][col];
                        col++;
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
