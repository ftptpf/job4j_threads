package ru.job4j.multithreading.synchronization.parse.sample;

import java.io.*;
import java.nio.file.Path;

/**
 * Пример неправильного парсера файла, у которого нужно исправить:
 * - Избавиться от get set за счет передачи File в конструктор.
 * - Ошибки в многопоточности. Сделать класс Immutable. Все поля final.
 * - Ошибки в IO. Не закрытые ресурсы. Чтение и запись файла без буфера.
 * - Нарушен принцип единой ответственности. Тут нужно сделать два класса.
 * - Методы getContent написаны в стиле копипаста. Нужно применить шаблон стратегия. content(Predicate<Character> filter)
 */
public class ParseFileWrongSample {
    private File file;

    public synchronized void setFile(File f) {
        file = f;
    }

    public synchronized File getFile() {
        return file;
    }

    public String getContent() throws IOException {
        InputStream i = new FileInputStream(file);
        String output = "";
        int data;
        while ((data = i.read()) > 0) {
            output += (char) data;
        }
        return output;
    }

    public String getContentWithoutUnicode() throws IOException {
        InputStream i = new FileInputStream(file);
        String output = "";
        int data;
        while ((data = i.read()) > 0) {
            if (data < 0x80) {
                output += (char) data;
            }
        }
        return output;
    }

    public void saveContent(String content) throws IOException {
        File resultFile = Path.of("src", "resources", "multithreading", "atomic", "result.txt").toFile();
        OutputStream o = new FileOutputStream(resultFile);
        for (int i = 0; i < content.length(); i += 1) {
            o.write(content.charAt(i));
        }
    }

    public static void main(String[] args) throws IOException {
        ParseFileWrongSample pf = new ParseFileWrongSample();
        File file = Path.of("src", "resources", "multithreading", "atomic", "parseFile1.txt").toFile();
        pf.setFile(file);
        String st = pf.getContentWithoutUnicode();
        pf.saveContent(st);
    }

}
