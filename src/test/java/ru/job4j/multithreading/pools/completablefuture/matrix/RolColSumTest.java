package ru.job4j.multithreading.pools.completablefuture.matrix;

import org.junit.Test;


import java.util.Arrays;
import java.util.concurrent.ExecutionException;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;


public class RolColSumTest {

    int[][] array3 = {
            {1, 2, 3},
            {4, 5, 6},
            {7, 8, 9}
    };

    int[][] array4 = {
            {1, 2, 3, 4},
            {5, 6, 7, 8},
            {9, 10, 11, 12},
            {13, 14, 15, 16}
    };

    int[][] array5 = {
            {1, 2, 3, 4, 5},
            {6, 7, 8, 9, 10},
            {11, 12, 13, 14, 15},
            {16, 17, 18, 18, 20},
            {21, 22, 23, 24, 25}
    };

    String result3 = "[Sums{rowSum=6, colSum=12}, Sums{rowSum=15, colSum=15}, Sums{rowSum=24, colSum=18}]";
    String result4 = "[Sums{rowSum=10, colSum=28}, Sums{rowSum=26, colSum=32}, Sums{rowSum=42, colSum=36}, Sums{rowSum=58, colSum=40}]";
    String result5 = "[Sums{rowSum=15, colSum=55}, Sums{rowSum=40, colSum=60}, Sums{rowSum=65, colSum=65}, Sums{rowSum=89, colSum=69}, Sums{rowSum=115, colSum=75}]";

    @Test
    public void sumSynchronousTest3() {
        RolColSum.Sums[] sums = RolColSum.synchronousSum(array3);
        assertThat(Arrays.toString(sums), is(result3));
    }

    @Test
    public void sumSynchronousTest4() {
        RolColSum.Sums[] sums = RolColSum.synchronousSum(array4);
        assertThat(Arrays.toString(sums), is(result4));
    }

    @Test
    public void sumSynchronousTest5() {
        RolColSum.Sums[] sums = RolColSum.synchronousSum(array5);
        assertThat(Arrays.toString(sums), is(result5));
    }

    @Test
    public void sumAsynchronousTest3() throws ExecutionException, InterruptedException {
        RolColSum.Sums[] sums = RolColSum.asynchronousSum(array3);
        assertThat(Arrays.toString(sums), is(result3));
    }

    @Test
    public void sumAsynchronousTest4() throws ExecutionException, InterruptedException {
        RolColSum.Sums[] sums = RolColSum.asynchronousSum(array4);
        assertThat(Arrays.toString(sums), is(result4));
    }

    @Test
    public void sumAsynchronousTest5() throws ExecutionException, InterruptedException {
        RolColSum.Sums[] sums = RolColSum.asynchronousSum(array5);
        assertThat(Arrays.toString(sums), is(result5));
    }
}