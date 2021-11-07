package ru.job4j.multithreading.threads;

/**
 * Имитация счетчика загрузки от 0 % до 100 %.
 * Каждую секунду на консоли обновляется информация.
 * Вывод с обновлением в одной и тоже же строке.
 */
public class WgetSimulation {
    public static void main(String[] args) {
        Thread thread = new Thread(
                () -> {
                    for (int i = 0; i <= 100; i++) {
                        try {
                            System.out.print("\rLoading: " + i + "%");
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
        );
        thread.start();
    }
}
