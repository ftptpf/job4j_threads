package ru.job4j.multithreading.nonblocking.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * В кеше с помощью version реализована возможность проверять актуальность данных.
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
        return memory.computeIfPresent(model.getId(), (id, value) -> {
            if (value.getVersion() != model.getVersion()) {
                throw new OptimisticException("version has already changed");
            }
            Base newValue = new Base(model.getId(), model.getVersion() + 1);
            newValue.setName(model.getName());
            return newValue;
                }
                ) != null;
    }

    public void delete(Base model) {
        memory.remove(model.getId());
    }

    @Override
    public String toString() {
        return "Cache{"
                + "memory=" + memory
                + '}';
    }
}
