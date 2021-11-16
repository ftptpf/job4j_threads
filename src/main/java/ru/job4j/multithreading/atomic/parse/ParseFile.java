package ru.job4j.multithreading.atomic.parse;

import java.io.*;
import java.nio.file.Path;
import java.util.function.Predicate;

/**
 * Парсим файл по предикату и сохраняем в другой файл.
 * Класс Immutable.
 */
public final class ParseFile {
    private final File fileParse;

    public ParseFile(File fileParse) {
        this.fileParse = fileParse;
    }

    public String getContent(Predicate<Character> filter) throws IOException {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(fileParse))) {
            int data;
            char ch;
            while ((data = br.read()) != -1) {
                ch = (char) data;
                if (filter.test(ch)) {
                    sb.append(ch);
                }
            }
        }
        return sb.toString();
    }

    public static void main(String[] args) throws IOException {
        File file = Path.of("src", "resources", "multithreading", "atomic", "parseFile.txt").toFile();
        File resultFile = Path.of("src", "resources", "multithreading", "atomic", "resultParseFile.txt").toFile();
        Predicate<Character> predicateUnicode = character -> (int) character < 128;

        ParseFile parseFile = new ParseFile(file);
        SaveFile saveFile = new SaveFile(resultFile);
        String content = parseFile.getContent(predicateUnicode);
        saveFile.save(content);
    }
}
