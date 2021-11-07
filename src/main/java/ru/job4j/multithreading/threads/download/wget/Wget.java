package ru.job4j.multithreading.threads.download.wget;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

/**
 * Упрошенный аналог wget.
 * Программа скачивает файл из сети с заданным ограничением по скорости.
 * На вход программы передается url и скорость скачивания в байт / секунд .
 */
public class Wget implements Runnable {
    private final String url;
    private final int speed;

    public Wget(String url, int speed) {
        this.url = url;
        this.speed = speed;
    }

    @Override
    public void run() {
        try (BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
             FileOutputStream out = new FileOutputStream("src/resources/multithreading/threads/"
                     + url.substring(url.lastIndexOf('/') + 1))) {
            int size = speed;
            byte[] dataBuffer = new byte[size];
            int byteRead;
            long before;
            long after;
            double time;
            double speedReal;
            int fileSize = 0;
            int step = 0;
            long start = System.currentTimeMillis();
            while ((before = System.nanoTime()) != 0 && (byteRead = in.read(dataBuffer, 0, size)) != -1) {
                out.write(dataBuffer, 0, byteRead);
                fileSize += byteRead;
                step++;
                after = System.nanoTime();
                time = (double) (after - before) / 1_000_000_000;
                speedReal = size / time;
                if (speedReal > speed) {
                    long sleepTime = (int) (1_000 - (size / (speedReal - speed)));
                    System.out.println("Скачали " + byteRead + " байт, на скорости загрузки " + speedReal + " байт в секунду. "
                            + "Ставим на паузу на " + sleepTime + " миллисекунд.");
                    Thread.sleep(sleepTime);
                }
            }
            long finish = System.currentTimeMillis();
            System.out.println(System.lineSeparator());
            System.out.println("Скачен файл объемом " + fileSize + " байт, за " + step + " шагов, за " + (double) (finish - start) / 1_000 + " сек.");
            System.out.println("При заданной скорости скачивания " + speed + " байт в секунду.");
            System.out.println("Расчетное время скачивания должно быть " + (double) fileSize / speed + " сек.");

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Interrupt");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) throws InterruptedException {
        String url = args[0];
        int speed = Integer.parseInt(args[1]);
        Thread wget = new Thread(new Wget(url, speed), "Thread wget");
        wget.start();
        wget.join();
    }
}
