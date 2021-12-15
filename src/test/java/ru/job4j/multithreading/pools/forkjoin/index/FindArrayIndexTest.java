package ru.job4j.multithreading.pools.forkjoin.index;

import org.junit.Test;

import java.util.concurrent.ForkJoinPool;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

public class FindArrayIndexTest {

    @Test
    public void testIntegerNonFound() {
        ForkJoinPool forkJoinPool = ForkJoinPool.commonPool();
        Integer[] array = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23};
        int result = forkJoinPool.invoke(new FindArrayIndex<>(array, 24));
        assertThat(result, is(-1));
    }

    @Test
    public void testIntegerFound() {
        ForkJoinPool forkJoinPool = ForkJoinPool.commonPool();
        Integer[] array = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23};
        int result = forkJoinPool.invoke(new FindArrayIndex<>(array, 23));
        assertThat(result, is(23));
    }

    @Test
    public void testStringFound() {
        ForkJoinPool forkJoinPool = ForkJoinPool.commonPool();
        String[] array = {"sdt0", "sdt1", "sdt2", "sdt3", "sdt4", "sdt5", "sdt6", "sdt7", "sdt8", "sdt9", "sdt10", "sdt11",
                "sdt12", "sdt13", "sdt14", "sdt15", "sdt16", "sdt17", "sdt18", "sdt19", "sdt20", "sdt21", "sdt22", "sdt23"};
        int result = forkJoinPool.invoke(new FindArrayIndex<>(array, "sdt21"));
        assertThat(result, is(21));
    }
}