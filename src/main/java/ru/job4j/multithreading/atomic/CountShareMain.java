package ru.job4j.multithreading.atomic;

/**
 * Класс с двумя нитями которые увеличивают счетчик.
 */
public class CountShareMain {
    public static void main(String[] args) throws InterruptedException {
        Count count = new Count();
        Thread first = new Thread(count::increment);
        Thread second = new Thread(count::increment);
        first.start();
        second.start();
        first.join();
        second.join();
        System.out.println(count.get());
    }
}
