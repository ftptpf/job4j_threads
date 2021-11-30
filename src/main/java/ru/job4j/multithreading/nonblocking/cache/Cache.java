package ru.job4j.multithreading.nonblocking.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * В кеше с помощью version реализована возможность проверять актуальность данных.
 * Это поле увеличивается на единицу каждый раз, когда модель изменили,
 * и даже если все поля остались не измененными поле version должно увеличиться на 1.
 * В кеше перед обновлением данных проверяем поле version.
 * Если version у модели и в кеше одинаковы, то можно обновить.
 * Если нет, то выбрасываем OptimisticException.
 */
public class Cache {
    private final Map<Integer, Base> memory = new ConcurrentHashMap<>();

    public boolean add(Base model) {
        return memory.putIfAbsent(model.getId(), model) == null;
    }

    public boolean update(Base model) {
        return true;
    }

    public void delete(Base model) {
        memory.remove(model.getId());
    }

    private void increment(Base model) {
/*        int value;
        do {
            value = model.getVersion();
        } while ())*/
    }
}
