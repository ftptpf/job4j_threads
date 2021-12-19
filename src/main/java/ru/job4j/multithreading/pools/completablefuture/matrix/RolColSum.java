package ru.job4j.multithreading.pools.completablefuture.matrix;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * Подсчет суммы по строкам и столбцам квадратной матрицы.
 * sums[i].rowSum - сумма элементов по i строке
 * sums[i].colSum - сумма элементов по i столбцу
 */
public class RolColSum {

    /**
     * Последовательный расчет сумм i-х столбцов и строк.
     * Расчет идет с верхнего левого угла к нижнему правому.
     * @param matrix двухмерный квадратный массив
     * @return массив сумм
     */
    public static Sums[] synchronousSum(int[][] matrix) {
        int n = matrix.length;
        Sums[] sums = new Sums[n];
        for (int i = 0; i < matrix.length; i++) {
            Sums s = new Sums();
            s.setColSum(sumCol(matrix, i));
            s.setRowSum(sumRow(matrix, i));
            sums[i] = s;
        }
        return sums;
    }

    /**
     * Считаем сумму значений ОДНОЙ заданной строки.
     * @param matrix двухмерный квадратный массив
     * @param rowIndex индекс строки
     * @return сумма значений строки
     */
    public static int sumRow(int[][] matrix, int rowIndex) {
        int sum = 0;
        for (int j = 0; j < matrix.length; j++) {
            sum += matrix[rowIndex][j];
        }
        return sum;
    }

    /**
     * Считаем сумму значений ОДНОГО заданного столбца.
     * @param matrix двухмерный квадратный массив
     * @param colIndex индекс столбца
     * @return сумма значений столбца
     */
    public static int sumCol(int[][] matrix, int colIndex) {
        int sum = 0;
        for (int i = 0; i < matrix.length; i++) {
            sum += matrix[i][colIndex];
        }
        return sum;
    }

    /**
     * Асинхронный расчет сумм i-х столбцов и строк.
     * Расчет идет с верхнего левого угла к нижнему правому,
     * навстречу идет асинхронный расчет с нижнего правого угла к верхнему левому.
     * @param matrix двухмерный квадратный массив
     * @return массив сумм
     */
    public static Sums[] asynchronousSum(int[][] matrix) {
        int n = matrix.length;
        Sums[] sums = new Sums[n];
        Map<Integer, CompletableFuture<Sums>> futures = new HashMap<>();
        int half = n / 2;
        for (int i = 0; i < half + 1; i++) {
            futures.put(i, getTask(matrix, i));
            if (i < half) {
                futures.put(n - i - 1, getTask(matrix, i));
            }
        }
        for (Integer key : futures.keySet()) {
            Sums s = new Sums();
            s.setColSum(sumCol(matrix, key));
            s.setRowSum(sumRow(matrix, key));
            sums[key] = s;
        }
        return sums;
    }

    private static CompletableFuture<Sums> getTask(int[][] matrix, int index) {
        return CompletableFuture.supplyAsync(
                () -> {
                    Sums s = new Sums();
                    s.setColSum(sumCol(matrix, index));
                    s.setRowSum(sumRow(matrix, index));
                    return s;
                }
        );
    }

    public static class Sums {
        private int rowSum;
        private int colSum;

        public void setRowSum(int rowSum) {
            this.rowSum = rowSum;
        }

        public void setColSum(int colSum) {
            this.colSum = colSum;
        }

        @Override
        public String toString() {
            return "Sums{"
                    + "rowSum=" + rowSum
                    + ", colSum=" + colSum
                    + '}';
        }
    }
}
