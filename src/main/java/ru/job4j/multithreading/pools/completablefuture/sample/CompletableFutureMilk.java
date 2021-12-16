package ru.job4j.multithreading.pools.completablefuture.sample;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * Пример supplyAsync(). Вы очень занятой человек и часто берете работу на дом. Но дома тоже есть свои дела.
 * Вы поручили сыну купить молоко. Вот как это будет выглядеть.
 * Для получения результата, используем метод get().
 */
public class CompletableFutureMilk {
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
     * @param product название продукта
     * @return supplyAsync() возвращает CompletableFuture<T>
     */
    public static CompletableFuture<String> buyProduct(String product) {
        return CompletableFuture.supplyAsync(
                () -> {
                    System.out.println("Сын: Мам/Пам, я пошел в магазин");
                    try {
                        TimeUnit.SECONDS.sleep(5);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                    System.out.println("Сын: Мам/Пап, я купил " + product);
                    return product;
                }
        );
    }

    /**
     * Для получения результата, нужно использовать метод get()
     * @throws Exception
     */
    public static void supplyAsyncExample() throws Exception {
        CompletableFuture<String> bm = buyProduct("Молоко");
        iWork();
        System.out.println("Куплено: " + bm.get());
    }

    /**
     * Если мы не хотим запускать отдельную задачу, а хотите, чтобы просто было выполнено какое-то действие,
     * используем метод thenAccept(), этот метод имеет доступ к результату CompletableFuture.
     * @throws Exception
     */
    public static void thenAcceptExample() throws Exception {
        CompletableFuture<String> bm = buyProduct("Молоко");
        bm.thenAccept((product) -> System.out.println("Сын: Я убрал " + product + " в холодильник "));
        iWork();
        System.out.println("Куплено: " + bm.get());
    }

    /**
     * thenApply() - этот метод принимает Function. Также имеет доступ к результату.
     * Как раз благодаря этому, мы можем произвести преобразование полученного результата.
     * Например сделаем чтобы после того, как сын принес молоко, налил вам его в кружку.
     * Однако результат преобразования станет доступным только при вызове get().
     * @throws Exception
     */
    public static void thenApplyExample() throws Exception {
        CompletableFuture<String> bm = buyProduct("Молоко")
                .thenApply((product) -> "Сын: я налил тебе в кружку " + product + ". Держи.");
        iWork();
        System.out.println(bm.get());

    }

    /**
     * thenCompose() - данный метод используется, если действия зависимы.
     * Т.е. сначала должно выполниться одно, а только потом другое.
     * Например, вам принципиально, чтобы сын сначала выбросил мусор, а только потом сходил за молоком.
     * @throws Exception
     */
    public static void thenComposeExample() throws Exception {
        CompletableFuture<Void> result = buyProduct("Молоко")
                .thenCompose(a -> CompletableFutureTrash.goToTrash());
        iWork();
    }

    /**
     * thenCombine() -Данный метод используется, если действия могут быть выполнены независимо друг от друга.
     * Причем в качестве второго аргумента, нужно передавать BiFunction – функцию,
     * которая преобразует результаты двух задач во что-то одно.
     * Например, первого сына вы посылаете выбросить мусор, а второго сходить за молоком.
     * @throws Exception
     */
    public static void thenCombineExample() throws Exception {
        CompletableFuture<String> result = buyProduct("Молоко")
                .thenCombine(buyProduct("Хлеб"), (r1, r2) -> "Куплены " + r1 + " и " + r2);
        iWork();
        System.out.println(result.get());
    }

    public static void main(String[] args) throws Exception {
        System.out.println("------BLOCK-1-supplyAsync()-----");
        supplyAsyncExample();
        TimeUnit.SECONDS.sleep(1);
        System.out.println("------BLOCK-2-thenAccept()-----");
        thenAcceptExample();
        TimeUnit.SECONDS.sleep(1);
        System.out.println("------BLOCK-3-thenApply()-----");
        thenApplyExample();
        TimeUnit.SECONDS.sleep(1);
        System.out.println("------BLOCK-4-thenCompose()-----");
        thenComposeExample();
        TimeUnit.SECONDS.sleep(1);
        System.out.println("------BLOCK-5-thenCombine()-----");
        thenCombineExample();
    }
}
