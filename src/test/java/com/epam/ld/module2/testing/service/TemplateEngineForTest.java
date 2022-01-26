package com.epam.ld.module2.testing.service;

import com.epam.ld.module2.testing.BaseClassForTest;
import com.epam.ld.module2.testing.TestResultExtension;
import com.epam.ld.module2.testing.models.Client;
import com.epam.ld.module2.testing.models.Template;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith({TestResultExtension.class})
public class TemplateEngineForTest extends BaseClassForTest {

    TemplateEngine templateEngine;
    @Mock
    Client client;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        templateEngine = new TemplateEngine();
        when(client.getAddresses()).thenReturn(TEST_ADDRESS);
    }

    @Test
    public void checkCreatingTemplateFromCorrectInput() {

        Template result = templateEngine.createTemplate(CORRECT_INPUT);

        assertEquals(EXAMPLE_TEMPLATE, result);
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "Subject: Test subject1\nSender: voronin@dlit.dp.ua\n",
            "Subject: Test subject2\n",
            "Sender: voronin@dlit.dp.ua\n",
            "Subject: Test subject\nTextWrong: Test message text\nSender: voronin@dlit.dp.ua\n"
    })
    public void checkCreatingTemplateFromWrongInput(String input) {

        Exception exception = assertThrows(IllegalArgumentException.class, () -> templateEngine.createTemplate(input));

        String expectedMessage = "Wrong input";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void checkCreatingTemplateWithNullValues() {

        String input = "Subject: \nText: Test message text\nSender: voronin@dlit.dp.ua\n";

        Exception exception = assertThrows(NullPointerException.class, () -> templateEngine.createTemplate(input));

        String expectedMessage = "One or more required fields are null";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void checkCreatingTemplateWhenValueEndsWithEndOfInput() {

        String input = "Subject: Test subject\nText: Test message text\nSender: voronin@dlit.dp.ua";

        Template result = templateEngine.createTemplate(input);

        assertEquals(EXAMPLE_TEMPLATE, result);
    }

    @Test
    public void checkCreatingTemplateWhenInputHasExtraFields() {

        String input = "Tag: Some tag\nSubject: Test subject\nText: Test message text\nSome field: test\nSender: voronin@dlit.dp.ua";

        Template result = templateEngine.createTemplate(input);

        assertEquals(EXAMPLE_TEMPLATE, result);
    }

    @Test
    public void checkIfGeneratedTemplateHasInputParams () throws FileNotFoundException {
        String message = templateEngine.generateMessage(EXAMPLE_TEMPLATE, client);
        assertTrue(message.contains(EXAMPLE_TEMPLATE.getSubject()));
        assertTrue(message.contains(EXAMPLE_TEMPLATE.getText()));
        assertTrue(message.contains(EXAMPLE_TEMPLATE.getSender()));
    }
}
