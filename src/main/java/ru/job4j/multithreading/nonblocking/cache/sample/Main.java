package ru.job4j.multithreading.nonblocking.cache.sample;

import ru.job4j.multithreading.nonblocking.cache.Base;

import java.util.HashMap;
import java.util.Map;

/**
 * Демонстрация на примере HashMap.
 */
public class Main {
    public static void main(String[] args) {
        Map<Integer, Base> map = new HashMap<>();
        Base base1 = new Base(1, 0);
        Base base2 = new Base(2, 1);
        map.put(base1.getId(), base1);
        map.put(base2.getId(), base2);

        Base user1 = map.get(1);
        user1.setName("User 1");

        Base user2 = map.get(2);
        user2.setName("User 2");

        map.put(user1.getId(), user1);
        map.put(user2.getId(), user2);
        System.out.println(map);
    }
}
