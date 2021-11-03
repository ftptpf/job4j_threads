package ru.job4j.multithreading.threads;

/**
 * Вывод имитации процесса загрузки в консоль.
 */
class ConsoleProgress implements Runnable {

    @Override
    public void run() {
        int count = 0;
        Thread.currentThread().setName("Load Thread");
        while (!Thread.currentThread().isInterrupted()) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                System.out.print(System.lineSeparator());
                System.out.print("Работа потока " + Thread.currentThread().getName() + " была прервана.");
                return;
            }
            if (count == 0) {
                System.out.print("\r load: \\");
                count = 1;
            } else if (count == 1) {
                System.out.print("\r load: |");
                count = 2;
            } else {
                System.out.print("\r load: /");
                count = 0;
            }
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
