package com.epam.ld.module2.testing.template;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

public class TemplateEngineTest {

    @Test
    public void checkCreatingTemplateFromCorrectInput () {
        String input = "Subject: Test subject\nText: Test message text\nSender: voronin@dlit.dp.ua\n";

        TemplateEngine templateEngine = new TemplateEngine();

        Template result = templateEngine.createTemplate(input);
        Template expected = new Template("Test subject", "Test message text", "voronin@dlit.dp.ua");

        assertEquals(expected, result);
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "Subject: Test subject1\nSender: voronin@dlit.dp.ua\n",
            "Subject: Test subject2\n",
            "Sender: voronin@dlit.dp.ua\n",
            "Subject: Test subject\nTextWrong: Test message text\nSender: voronin@dlit.dp.ua\n"
    })
    public void checkCreatingTemplateFromWrongInput (String input) {

        TemplateEngine templateEngine = new TemplateEngine();

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            templateEngine.createTemplate(input);
        });

        String expectedMessage = "Wrong input";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void checkCreatingTemplateWithNullValues () {
        TemplateEngine templateEngine = new TemplateEngine();

        String input = "Subject: \nText: Test message text\nSender: voronin@dlit.dp.ua\n";

        Exception exception = assertThrows(NullPointerException.class, () -> {
            templateEngine.createTemplate(input);
        });

        String expectedMessage = "One or more required fields are null";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

}
