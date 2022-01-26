package com.epam.ld.module2.testing.service;

import com.google.common.base.Charsets;
import com.google.common.io.Files;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class FileService {

    public static String OUTPUT_FILE = "output.html";

    public static String readFromFile(String fileName) throws FileNotFoundException {
        try {
            File file = new File(Objects.requireNonNull(FileService.class.getClassLoader().getResource(fileName)).getPath());

            return Files.toString(file, Charsets.UTF_8);
        } catch (IOException | NullPointerException e) {
            throw new FileNotFoundException("Can`t open or something wrong with file named: " + fileName);
        }
    }

    public static void writeToFile(String content) throws IOException {
        File file = new File(OUTPUT_FILE);
        Files.write(content.getBytes(StandardCharsets.UTF_8), file);
    }
}
