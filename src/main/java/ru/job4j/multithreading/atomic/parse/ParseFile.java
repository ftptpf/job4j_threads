package ru.job4j.multithreading.atomic.parse;

import java.io.*;
import java.util.function.Predicate;

/**
 * Парсер файла.
 */
public final class ParseFile {
    private final File file;

    public ParseFile(File file) {
        this.file = file;
    }

    public String getContentWithoutUnicode() throws IOException {
        StringBuilder sb = new StringBuilder();
        Predicate<Integer> predicate =
        try (BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(file))) {
            inputStream.read()

        }
/*        InputStream i = new FileInputStream(file);
        String output = "";
        int data;
        while ((data = i.read()) > 0) {
            if (data < 0x80) {
                output += (char) data;
            }
        }*/
        return sb.toString();
    }

    public void saveContent(String content) throws IOException {
        OutputStream o = new FileOutputStream(file);
        for (int i = 0; i < content.length(); i += 1) {
            o.write(content.charAt(i));
        }
    }
}
