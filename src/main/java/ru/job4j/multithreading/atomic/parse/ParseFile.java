package ru.job4j.multithreading.atomic.parse;

import java.io.*;
import java.nio.file.Path;
import java.util.function.Predicate;

/**
 * Парсер файла.
 */
public final class ParseFile {
    private final File fileParse;
    private final File fileResult;

    public ParseFile(File fileParse, File fileResult) {
        this.fileParse = fileParse;
        this.fileResult = fileResult;
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


    public void saveContent(String content) throws IOException {
        try (BufferedWriter out = new BufferedWriter(new FileWriter(fileResult))) {
            out.write(content);

        }
    }

    public static void main(String[] args) throws IOException {
        File file = Path.of("src", "resources", "multithreading", "atomic", "parseFile1.txt").toFile();
        File resultFile = Path.of("src", "resources", "multithreading", "atomic", "result.txt").toFile();
        Predicate<Character> predicateUnicode = character -> (int) character < 128;

        ParseFile parseFile = new ParseFile(file, resultFile);
        String content = parseFile.getContent(predicateUnicode);
        parseFile.saveContent(content);
    }


}
