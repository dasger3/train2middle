package com.epam.ld.module2.testing.template;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TemplateEngineTest {

    @Test
    public void checkCreatingTemplateFromCorrectInput () {
        String input = "Subject: Test subject\nText: Test message text\nSender: voronin@dlit.dp.ua";

        TemplateEngine templateEngine = new TemplateEngine();

        Template result = templateEngine.createTemplate(input);
        Template expected = new Template("Test subject", "Test message text", "voronin@dlit.dp.ua");

        assertEquals(expected, result);
    }
}
