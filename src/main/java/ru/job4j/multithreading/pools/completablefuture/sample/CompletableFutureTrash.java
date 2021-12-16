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

    public static CompletableFuture<Void> washHands(String name) {
        return CompletableFuture.runAsync(
                () -> {
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                    System.out.println(name + " , моет руки");
                });
    }

    /**
     * allOf() - возвращает ComputableFuture<Void>, при этом обеспечивает выполнение всех задач.
     * Например, вы зовете всех членов семью к столу. Надо дождаться пока все помоют руки
     * @throws Exception
     */
    public static void allOfExample() throws Exception {
        CompletableFuture<Void> all = CompletableFuture.allOf(
                washHands("Папа"), washHands("Мама"),
                washHands("Ваня"), washHands("Боря")
        );
        TimeUnit.SECONDS.sleep(3);
    }

    public static CompletableFuture<String> whoWashHands(String name) {
        return CompletableFuture.supplyAsync(
                () -> {
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                    return name + ", моет руки";
                });
    }

    /**
     * anyOf() - возвращает ComputableFuture<Object>. Результатом будет первая выполненная задача.
     * На том же примере мы можем, например, узнать, кто сейчас моет руки.
     * Результаты запуск от запуска будут различаться.
     * @throws Exception
     */
    public static void anyOfExample() throws Exception {
        CompletableFuture<Object> first = CompletableFuture.anyOf(
                whoWashHands("Папа"), whoWashHands("Мама"),
                whoWashHands("Ваня"), whoWashHands("Боря")
        );
        System.out.println("Кто сейчас моет руки?");
        TimeUnit.SECONDS.sleep(1);
        System.out.println(first.get());
    }

    public static void main(String[] args) throws Exception {
        System.out.println("------BLOCK-1-runAsync()-----");
        runAsyncExample();
        TimeUnit.SECONDS.sleep(1);
        System.out.println("------BLOCK-2-thenRun()-----");
        thenRunExample();
        TimeUnit.SECONDS.sleep(1);
        System.out.println("------BLOCK-3-allOf()-----");
        allOfExample();
        TimeUnit.SECONDS.sleep(1);
        System.out.println("------BLOCK-4-anyOf()-----");
        anyOfExample();
    }
}
