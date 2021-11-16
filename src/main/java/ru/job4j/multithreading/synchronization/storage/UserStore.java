package ru.job4j.multithreading.synchronization.storage;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.HashSet;
import java.util.Set;

@ThreadSafe
public class UserStore {
    @GuardedBy("this")
    private final Set<User> set = new HashSet<>();

    public synchronized boolean add(User user) {
        return set.add(user);
    }

    public boolean update(User user) {
        boolean result = false;
        if (set.contains(user)) {
            set.remove(user);
            result = set.add(user);
        }
        return result;
    }

    public boolean delete(User user) {
        return set.remove(user);
    }

    public boolean transfer(int fromId, int toId, int amount) {


        return true;
    }
}
