package ru.job4j.multithreading.synchronization.storage;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.HashSet;
import java.util.Set;

/**
 *
 */
@ThreadSafe
public final class UserStore {
    @GuardedBy("this")
    private final Set<User> set = new HashSet<>();

    public synchronized boolean add(User user) {
        return set.add(user);
    }

    public synchronized boolean find(User user) {
        return true;
    }

    public synchronized boolean update(User user) {
        boolean result = false;
        if (set.contains(user)) {
            delete(user);
            result = add(user);
        }
        return result;
    }

    public synchronized boolean delete(User user) {
        return set.remove(user);
    }

    public synchronized boolean transfer(int fromId, int toId, int amount) {
        boolean result = false;
        User userFrom = new User(-1, -1);
        User userTo = new User(-2, -2);
        for (User user : set) {
            if (user.getId() == fromId) {
                userFrom = user;
            } else if (user.getId() == toId) {
                userTo = user;
            }
        }
        if ((userFrom.getId() & userTo.getId()) >= 0
                && userFrom.getId() != userTo.getId()
                && userFrom.getAmount() >= amount) {
            userFrom.setAmount(userFrom.getAmount() - amount);
            userTo.setAmount(userTo.getAmount() + amount);
            update(userFrom);
            update(userTo);
            result = true;
        }
        return result;
    }
}
