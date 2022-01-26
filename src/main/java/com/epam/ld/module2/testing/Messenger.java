package com.epam.ld.module2.testing;


import com.epam.ld.module2.testing.models.Client;
import com.epam.ld.module2.testing.models.Template;
import com.epam.ld.module2.testing.server.MailServer;
import com.epam.ld.module2.testing.service.TemplateEngine;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;

/**
 * The type Messenger.
 */
@Getter
@Setter
public class Messenger {
    private MailServer mailServer;
    private TemplateEngine templateEngine;

    /**
     * Instantiates a new Messenger.
     *
     * @param mailServer     the mail server
     * @param templateEngine the template engine
     */
    public Messenger(MailServer mailServer,
                     TemplateEngine templateEngine) {
        this.mailServer = mailServer;
        this.templateEngine = templateEngine;
    }

    /**
     * Send message.
     *
     * @param client   the client
     * @param template the template
     */
    public void sendMessage(Client client, Template template) throws IOException {
        String messageContent = templateEngine.generateMessage(template, client);
        mailServer.send(client.getAddresses(), messageContent);
    }
}