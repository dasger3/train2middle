package com.epam.ld.module2.testing;

import com.epam.ld.module2.testing.models.Client;
import com.epam.ld.module2.testing.models.Template;
import com.epam.ld.module2.testing.server.MailServer;
import com.epam.ld.module2.testing.service.TemplateEngine;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class MessengerTest extends BaseClassForTest {

    @Mock
    MailServer mailServer;
    @Mock
    Client client;
    @Spy
    TemplateEngine templateEngine;
    @Mock
    Template template;
    @InjectMocks
    Messenger messenger;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);

        when(client.getAddresses()).thenReturn(TEST_ADDRESS);
        doReturn(template).when(templateEngine).createTemplate(TEST_INPUT);
    }

    @Test
    public void checkIfGeneratedTemplateSends() throws IOException {
        doReturn(TEST_TEMPLATE).when(templateEngine).generateMessage(template, client);

        messenger.sendMessage(client, templateEngine.createTemplate(TEST_INPUT));

        verify(templateEngine).createTemplate(TEST_INPUT);
        verify(templateEngine).generateMessage(template, client);
        verify(mailServer).send(TEST_ADDRESS, TEST_TEMPLATE);
    }

    @Test
    public void checkIfTemplateDoNotExists() throws IOException {
        doReturn(WRONG_PATH_TO_TEMPLATE).when(templateEngine).getPathTemplate();

        Exception exception = assertThrows(IOException.class, () -> messenger.sendMessage(client, template));

        String expectedMessage = "Can`t open or something wrong with file named: " + WRONG_PATH_TO_TEMPLATE;
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
        verify(mailServer, never()).send(TEST_ADDRESS, TEST_TEMPLATE);
    }
}
