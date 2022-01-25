package com.epam.ld.module2.testing;


import com.epam.ld.module2.testing.server.ConsoleMailServer;
import com.epam.ld.module2.testing.server.FileMailServer;
import com.epam.ld.module2.testing.server.MailServer;
import com.epam.ld.module2.testing.template.Template;
import com.epam.ld.module2.testing.template.TemplateEngine;

import java.io.FileNotFoundException;

/**
 * The type Messenger.
 */
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
    public void sendMessage(Client client, Template template) throws FileNotFoundException {
            String messageContent =
                    templateEngine.generateMessage(template, client);
            mailServer.send(client.getAddresses(), messageContent);
    }

    public static void main(String[] args) {
        TemplateEngine templateEngine = new TemplateEngine();
        Messenger messenger;
        MailServer mailServer;

        if (args.length > 0) {
            System.out.println("File mode");
            mailServer = new FileMailServer();
        }
        else {
            System.out.println("Console mode");
            mailServer = new ConsoleMailServer();
        }

    }
}