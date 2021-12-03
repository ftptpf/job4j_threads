package ru.job4j.multithreading.nonblocking.cache;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

public class CacheTest {

    @Test
    public void test() throws InterruptedException {
        Cache cache = new Cache();
        Base base = new Base(1, 1);
        base.setName("Base");
        cache.add(base);

        Thread first = new Thread(
                () -> {
                    Base baseOne = new Base(1, 1);
                    baseOne.setName("Base #1");
                    try {
                        cache.update(baseOne);
                    } catch (OptimisticException oe) {
                        Assert.assertNotEquals("", oe.getMessage());
                    }
                }, "First"
        );
        Thread second = new Thread(
                () -> {
                    Base baseTwo = new Base(1, 1);
                    baseTwo.setName("Base #2");
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                    try {
                        cache.update(baseTwo);
                    } catch (OptimisticException oe) {
                        Assert.assertNotEquals("", oe.getMessage());
                    }
                }, "Second"
        );
        first.start();
        second.start();
        first.join();
        second.join();
        assertThat(cache.toString(), is("Cache{memory={1=Base{id=1, version=2, name='Base #1'}}}"));
    }
}