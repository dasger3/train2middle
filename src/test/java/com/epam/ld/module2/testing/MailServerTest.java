package com.epam.ld.module2.testing;

import com.epam.ld.module2.testing.template.TemplateEngine;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MailServerTest {

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
    public void checkMailServerWorksInConsoleMode() {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        PrintStream console = System.out;
        try {
            System.setOut(new PrintStream(bytes));
            Messenger.main(new String[]{});
        } finally {
            System.setOut(console);
        }
        assertEquals("Console mode" + EOL, bytes.toString());
    }
}
