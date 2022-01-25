package com.epam.ld.module2.testing;

import com.epam.ld.module2.testing.template.TemplateEngine;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MailServerTest extends BaseClassTest{

    private static final String EOL = System.getProperty("line.separator");

    TemplateEngine templateEngine;
    Client client;

    @BeforeEach
    public void setUp() {
        templateEngine = new TemplateEngine();
        client = new Client("oleksii_voronin2@epam.com");
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
            Messenger.main(new String[]{});
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
            Messenger.main(new String[]{});
        } finally {
            System.setOut(console);
        }
        assertEquals("Wrong input" + EOL, bytesOut.toString());
    }

    @Test
    public void checkMailServerDoNotWorksWithWrongInputConsoleParams() {
        ByteArrayOutputStream bytesOut = new ByteArrayOutputStream();
        ByteArrayInputStream in = new ByteArrayInputStream(CORRECT_INPUT.getBytes());
        PrintStream console = System.out;

        try {
            System.setOut(new PrintStream(bytesOut));
            System.setIn(in);
            Messenger.main(new String[]{"first", "second", "third"});
        } finally {
            System.setOut(console);
        }
        assertEquals("Incorrect number of arguments" + EOL, bytesOut.toString());
    }
}
