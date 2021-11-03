package ru.job4j.multithreading.threads.download.sample;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

/**
 * Скачивание файла с ограничением скорости 1024 байт и задержкой 1 секунду.
 */
public class FileDownload {
    public static void main(String[] args) {
        String file = "https://raw.githubusercontent.com/peterarsentev/course_test/master/pom.xml";
        try (BufferedInputStream in = new BufferedInputStream(new URL(file).openStream());
             FileOutputStream fileOutputStream = new FileOutputStream("src/resources/multithreading/threads/pom_tmp.xml")) {
            byte[] dataBuffer = new byte[1024];
            int byteRead;
            while ((byteRead = in.read(dataBuffer, 0, 1024)) != -1) {
                fileOutputStream.write(dataBuffer, 0, byteRead);
                Thread.sleep(1000);
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

}
