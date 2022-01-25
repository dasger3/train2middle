package com.epam.ld.module2.testing.template;

import com.epam.ld.module2.testing.Client;
import com.epam.ld.module2.testing.Messenger;
import com.epam.ld.module2.testing.server.MailServer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class TemplateEngineTest {

    TemplateEngine templateEngine;
    Client client;

    private final String CORRECT_INPUT = "Subject: Test subject\nText: Test message text\nSender: voronin@dlit.dp.ua\n";
    private final Template EXAMPLE_TEMPLATE = new Template("Test subject", "Test message text", "voronin@dlit.dp.ua");

    @BeforeEach
    public void setUp() {
        templateEngine = new TemplateEngine();
        client = new Client("oleksii_voronin2@epam.com");
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

    @Test void checkIfGeneratedTemplateHasInputParams () throws FileNotFoundException {
        String message = templateEngine.generateMessage(EXAMPLE_TEMPLATE, client);
        assertTrue(message.contains(EXAMPLE_TEMPLATE.getSubject()));
        assertTrue(message.contains(EXAMPLE_TEMPLATE.getText()));
        assertTrue(message.contains(EXAMPLE_TEMPLATE.getSender()));
    }

    @Test
    public void checkIfGeneratedTemplateSends() throws FileNotFoundException {
        MailServer mailServer = mock(MailServer.class);

        Messenger messenger = new Messenger(mailServer, templateEngine);
        Template template = templateEngine.createTemplate(CORRECT_INPUT);

        messenger.sendMessage(client, template);

        verify(mailServer).send(eq(client.getAddresses()), anyString());
    }

    @Test
    public void checkIfTemplateDoNotExists() {
        MailServer mailServer = mock(MailServer.class);

        Messenger messenger = new Messenger(mailServer, templateEngine);
        Template template = templateEngine.createTemplate(CORRECT_INPUT);

        String badPart = "wrong path to file";

        templateEngine.setPathTemplate(badPart);

        Exception exception = assertThrows(FileNotFoundException.class, () -> messenger.sendMessage(client, template));

        String expectedMessage = "Can`t open or something wrong with file named: " + badPart;
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
        verify(mailServer, never()).send(eq(client.getAddresses()), anyString());
    }
}
