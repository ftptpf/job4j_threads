package ru.job4j.multithreading.pools.completablefuture.sample;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * Вы очень занятой человек и часто берете работу на дом. Но дома тоже есть свои дела.
 * Например, сходить выбросить мусор. Вам некогда этим заниматься, но у вас есть сын, который может это сделать.
 * Вы сами работаете, а ему поручаете это дело. Это проявление асинхронности, т.к. вы сами работаете,
 * а тем временем ваш сын выбрасывает мусор.
 */
public class CompletableFutureTrash {
    private static void iWork() throws InterruptedException {
        int count = 0;
        while (count < 10) {
            System.out.println("Вы: Я работаю.");
            TimeUnit.SECONDS.sleep(1);
            count++;
        }
    }

    /**
     * Для написания асинхронного кода используем класс CompletableFuture.
     * @return runAsync() возвращает CompletableFuture<Void>
     */
    public static CompletableFuture<Void> goToTrash() {
        return CompletableFuture.runAsync(
                () -> {
                    System.out.println("Сын: Мам/Пам, я пошел выносить мусор");
                    try {
                        TimeUnit.SECONDS.sleep(5);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                    System.out.println("Сын: Мам/Пап, я вернулся!");
                }
        );
    }

    public static void runAsyncExample() throws Exception {
        CompletableFuture<Void> gtt = goToTrash();
        iWork();
    }

    /**
     * thenRun() - ничего не возвращает, а позволяет выполнить задачу типа Runnable после выполнения асинхронной задачи.
     * @throws Exception
     */
    public static void thenRunExample() throws Exception {
        CompletableFuture<Void> gtt = goToTrash();
        gtt.thenRun(
                () -> {
                    int count = 0;
                    while (count < 3) {
                        System.out.println("Сын: я мою руки");
                        try {
                            TimeUnit.MICROSECONDS.sleep(500);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                        count++;
                    }
                    System.out.println("Сын: Я помыл руки");
                }
        );
        iWork();
    }


    public static void main(String[] args) throws Exception {
        System.out.println("------BLOCK-1-runAsync()-----");
        runAsyncExample();
        TimeUnit.SECONDS.sleep(1);
        System.out.println("------BLOCK-2-thenRun()-----");
        thenRunExample();
    }
}
