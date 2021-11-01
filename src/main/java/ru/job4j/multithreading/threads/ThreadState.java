package ru.job4j.multithreading.threads;

public class ThreadState {
    public static void main(String[] args) {
        Thread first = new Thread(
                () -> System.out.println(Thread.currentThread().getName())
        );
        first.start();
        Thread second = new Thread(
                () -> System.out.println(Thread.currentThread().getName())
        );
        second.start();
        while ((first.getState() != Thread.State.TERMINATED)
                || (second.getState() != Thread.State.TERMINATED)) {
            System.out.println("The threads in work.");
        }
        System.out.println("The work is done.");
    }
}
