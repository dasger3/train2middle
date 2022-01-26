package com.epam.ld.module2.testing.server;

import com.epam.ld.module2.testing.BaseClassForTest;
import com.epam.ld.module2.testing.Main;
import com.epam.ld.module2.testing.service.FileService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MailServerForTest extends BaseClassForTest {

    private static final String EOL = System.getProperty("line.separator");
    private static final String INPUT_FILE = "input.txt";
    private static final String OUTPUT_FILE = "output.html";

    @BeforeEach
    public void setUp() {
    }

    @AfterEach
    public void tearDown() {

    }

    @Test
    public void checkMailServerWorksInConsoleModeWithCorrectInput() {
        ByteArrayOutputStream bytesOut = new ByteArrayOutputStream();
        ByteArrayInputStream in = new ByteArrayInputStream(CORRECT_INPUT.getBytes());
        PrintStream console = System.out;

        try {
            System.setOut(new PrintStream(bytesOut));
            System.setIn(in);
            Main.main(new String[]{});
        } finally {
            System.setOut(console);
        }
        assertTrue(bytesOut.toString().contains("Mail sent to console" + EOL));
    }

    @Test
    public void checkMailServerWorksInConsoleModeWithIncorrectInput() {
        ByteArrayOutputStream bytesOut = new ByteArrayOutputStream();
        ByteArrayInputStream in = new ByteArrayInputStream("wrong input".getBytes());
        PrintStream console = System.out;

        try {
            System.setOut(new PrintStream(bytesOut));
            System.setIn(in);
            Main.main(new String[]{});
        } finally {
            System.setOut(console);
        }
        assertEquals("Wrong input" + EOL, bytesOut.toString());
    }

    @Test
    public void checkMailServerDoesNotWorkWithWrongInputConsoleParams() {
        ByteArrayOutputStream bytesOut = new ByteArrayOutputStream();
        ByteArrayInputStream in = new ByteArrayInputStream(CORRECT_INPUT.getBytes());
        PrintStream console = System.out;

        try {
            System.setOut(new PrintStream(bytesOut));
            System.setIn(in);
            Main.main(new String[]{"first", "second", "third"});
        } finally {
            System.setOut(console);
        }
        assertEquals("Incorrect number of arguments" + EOL, bytesOut.toString());
    }

    @Test
    public void checkMailServerWorksCorrectInFileMode(@TempDir Path tempDir) throws IOException {

        Path outputPath = tempDir.resolve(OUTPUT_FILE);

        Main.main(new String[]{INPUT_FILE, outputPath.toAbsolutePath().toString()});

        assertTrue(Files.exists(outputPath), "File should exist");

        StringBuilder s = new StringBuilder();
        Files.readAllLines(outputPath).forEach(s::append);
        String fullResultFile = s.toString();

        assertTrue(fullResultFile.contains(EXAMPLE_TEMPLATE.getSender()));
        assertTrue(fullResultFile.contains(EXAMPLE_TEMPLATE.getText()));
        assertTrue(fullResultFile.contains(EXAMPLE_TEMPLATE.getSubject()));
    }

    @Test
    public void checkIfFileWithInputNameDoesNotExist(@TempDir Path tempDir) {
        ByteArrayOutputStream bytesOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(bytesOut));
        Path outputPath = tempDir.resolve(OUTPUT_FILE);

        FileService.OUTPUT_FILE = outputPath.toAbsolutePath().toString();

        Main.main(new String[]{"wrong path", OUTPUT_FILE});
        assertEquals("Can`t open or something wrong with file named: wrong path" + EOL, bytesOut.toString());
    }


}
