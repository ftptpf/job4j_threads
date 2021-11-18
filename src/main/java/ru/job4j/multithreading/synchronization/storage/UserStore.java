package ru.job4j.multithreading.synchronization.storage;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.HashMap;
import java.util.Map;

/**
 * Перевод денег с одного счета пользователя на другой.
 * Операции атомарны. Объект монитора - потокобезопасный класс UserStore.
 */
@ThreadSafe
public final class UserStore {
    @GuardedBy("this")
    private final Map<Integer, User> map = new HashMap<>();

    public synchronized boolean add(User user) {
        map.put(user.getId(), user);
        return map.containsKey(user.getId());
    }

    public synchronized boolean update(User user) {
        boolean result = false;
        if (map.containsKey(user.getId())) {
            result = add(user);
        }
        return result;
    }

    public synchronized boolean delete(User user) {
        return map.remove(user.getId(), user);
    }

    public synchronized boolean transfer(int fromId, int toId, int amount) {
        boolean result = false;
        User userFrom = map.get(fromId);
        User userTo = map.get(toId);
        if (((userFrom != null & userTo != null)
                && userFrom.getId() != userTo.getId()
                && userFrom.getAmount() >= amount)) {
            userFrom.setAmount(userFrom.getAmount() - amount);
            userTo.setAmount(userTo.getAmount() + amount);
            update(userFrom);
            update(userTo);
            result = true;
        }
        return result;
    }
}
