package ru.job4j.multithreading.threads;

public class ThreadState {
    public static void main(String[] args) {
        //System.out.println(Thread.currentThread().getName());
        Thread first = new Thread(
                //() -> {}
        );
        System.out.println(first.getState());
        first.start();
        System.out.println(Thread.currentThread().getName());
        while (first.getState() != Thread.State.TERMINATED) {
            System.out.println(first.getState());
        }
        System.out.println(first.getState());
    }
}
