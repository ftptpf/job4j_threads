package ru.job4j.multithreading.nonblocking.cache;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

public class CacheTest {
    @Rule
    public ExpectedException expectedException = ExpectedException.none();

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
                    cache.update(baseOne);
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
                    cache.update(baseTwo);
                }, "Second"
        );
        first.start();
        second.start();
        first.join();
        second.join();
        expectedException.expect(OptimisticException.class);
        //expectedException.expectMessage("version has already changed");
        expectedException.expectMessage(is("version has already changed"));
        assertThat(cache.toString(), is("Cache{memory={1=Base{id=1, version=2, name='Base #1'}}}"));
    }
}