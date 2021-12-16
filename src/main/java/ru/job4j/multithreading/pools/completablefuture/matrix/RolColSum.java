package ru.job4j.multithreading.pools.completablefuture.matrix;

/**
 * Подсчет суммы по строкам и столбцам квадратной матрицы.
 * sums[i].rowSum - сумма элементов по i строке
 * sums[i].colSum - сумма элементов по i столбцу
 */
public class RolColSum {

    public static class Sums {
        private int rowSum;
        private int colSum;

        public int getRowSum() {
            return rowSum;
        }

        public void setRowSum(int rowSum) {
            this.rowSum = rowSum;
        }

        public int getColSum() {
            return colSum;
        }

        public void setColSum(int colSum) {
            this.colSum = colSum;
        }
    }

    /**
     * Последовательный расчет.
     * @param matrix
     * @return
     */
    public static Sums[] sum(int[][] matrix) {
        return null;
    }

    /**
     * Асинхронный расчет.
     * @param matrix
     * @return
     */
    public static Sums[] asyncSum(int[][] matrix) {
        return null;
    }
}
