package ru.job4j.multithreading.synchronization.parse;

import java.io.*;

/**
 * Сохраняем строку в файл.
 * Класс Immutable.
 */
public final class SaveFile {
    private final File fileResult;

    public SaveFile(File fileResult) {
        this.fileResult = fileResult;
    }

    public void save(String context) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileResult)))  {
            bw.write(context);
        }
    }
}
