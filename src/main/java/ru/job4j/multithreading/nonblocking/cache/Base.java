package ru.job4j.multithreading.nonblocking.cache;

/**
 * Базовая модель данных.
 * ID - уникальный идентификатор, в системе будет только один объект с таким ID.
 * version - определяет достоверность версии в кеше.
 * name - это поле бизнес модели.
 */
public class Base {
    private final int id;
    private final int version;
    private String name;

    public Base(int id, int version) {
        this.id = id;
        this.version = version;
    }

    public int getId() {
        return id;
    }

    public int getVersion() {
        return version;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Base{"
                + "id=" + id
                + ", version=" + version
                + ", name='" + name + '\''
                + '}';
    }
}
