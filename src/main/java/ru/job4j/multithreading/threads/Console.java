package ru.job4j.multithreading.threads;

/**
 * Вывод имитации процесса загрузки в консоль.
 */
class ConsoleProgress implements Runnable {

    @Override
    public void run() {
        int count = 0;
        char[] array = new char[] {'\\', '|', '/'};
        Thread.currentThread().setName("Load Thread");
        while (!Thread.currentThread().isInterrupted()) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            System.out.print("\r load: " + array[count]);
            count = (count < 2) ? (count + 1) : 0;
        }
    }
}

public class Console {
    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(new ConsoleProgress());
        thread.start();
        Thread.sleep(5000);
        thread.interrupt();
    }
}
