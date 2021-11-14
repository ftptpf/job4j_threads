package ru.job4j.multithreading.atomic.parse;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

public class ParseFileSave {
    private final File fileResult;
    private final OutputStream out;

    public ParseFileSave(File fileResult, OutputStream out) {
        this.fileResult = fileResult;
        this.out = out;
    }

    public void save(String context) throws IOException {
        out.write(Integer.parseInt(context));
    }
}
