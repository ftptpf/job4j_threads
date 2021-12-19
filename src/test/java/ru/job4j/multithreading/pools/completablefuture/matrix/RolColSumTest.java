package ru.job4j.multithreading.pools.completablefuture.matrix;

import org.junit.Test;

import java.util.Arrays;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;


public class RolColSumTest {

    @Test
    public void sumSynchronousTest3() {
        int[][] array = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };
        String result = "[Sums{rowSum=6, colSum=12}, Sums{rowSum=15, colSum=15}, Sums{rowSum=24, colSum=18}]";
        RolColSum.Sums[] sums = RolColSum.synchronousSum(array);
        assertThat(Arrays.toString(sums), is(result));
    }

    @Test
    public void sumSynchronousTest4() {
        int[][] array = {
                {1, 2, 3, 4},
                {5, 6, 7, 8},
                {9, 10, 11, 12},
                {13, 14, 15, 16}
        };
        String result = "[Sums{rowSum=10, colSum=28}, Sums{rowSum=26, colSum=32}, Sums{rowSum=42, colSum=36}, "
                + "Sums{rowSum=58, colSum=40}]";
        RolColSum.Sums[] sums = RolColSum.synchronousSum(array);
        assertThat(Arrays.toString(sums), is(result));
    }

    @Test
    public void sumSynchronousTest5() {
        int[][] array = {
                {1, 2, 3, 4, 5},
                {6, 7, 8, 9, 10},
                {11, 12, 13, 14, 15},
                {16, 17, 18, 18, 20},
                {21, 22, 23, 24, 25}
        };
        String result = "[Sums{rowSum=15, colSum=55}, Sums{rowSum=40, colSum=60}, Sums{rowSum=65, colSum=65}, "
                + "Sums{rowSum=89, colSum=69}, Sums{rowSum=115, colSum=75}]";
        RolColSum.Sums[] sums = RolColSum.synchronousSum(array);
        assertThat(Arrays.toString(sums), is(result));
    }

    @Test
    public void sumAsynchronousTest3() {
        int[][] array = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };
        String result = "[Sums{rowSum=6, colSum=12}, Sums{rowSum=15, colSum=15}, Sums{rowSum=24, colSum=18}]";
        RolColSum.Sums[] sums = RolColSum.asynchronousSum(array);
        assertThat(Arrays.toString(sums), is(result));
    }

    @Test
    public void sumAsynchronousTest4() {
        int[][] array = {
                {1, 2, 3, 4},
                {5, 6, 7, 8},
                {9, 10, 11, 12},
                {13, 14, 15, 16}
        };
        String result = "[Sums{rowSum=10, colSum=28}, Sums{rowSum=26, colSum=32}, Sums{rowSum=42, colSum=36}, "
                + "Sums{rowSum=58, colSum=40}]";
        RolColSum.Sums[] sums = RolColSum.asynchronousSum(array);
        assertThat(Arrays.toString(sums), is(result));
    }

    @Test
    public void sumAsynchronousTest5() {
        int[][] array = {
                {1, 2, 3, 4, 5},
                {6, 7, 8, 9, 10},
                {11, 12, 13, 14, 15},
                {16, 17, 18, 18, 20},
                {21, 22, 23, 24, 25}
        };
        String result = "[Sums{rowSum=15, colSum=55}, Sums{rowSum=40, colSum=60}, Sums{rowSum=65, colSum=65}, "
                + "Sums{rowSum=89, colSum=69}, Sums{rowSum=115, colSum=75}]";
        RolColSum.Sums[] sums = RolColSum.asynchronousSum(array);
        assertThat(Arrays.toString(sums), is(result));
    }
}
