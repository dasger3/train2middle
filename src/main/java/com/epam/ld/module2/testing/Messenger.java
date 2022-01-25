package com.epam.ld.module2.testing;


import com.epam.ld.module2.testing.server.ConsoleMailServer;
import com.epam.ld.module2.testing.server.FileMailServer;
import com.epam.ld.module2.testing.server.MailServer;
import com.epam.ld.module2.testing.template.Template;
import com.epam.ld.module2.testing.template.TemplateEngine;
import lombok.Getter;
import lombok.Setter;

import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * The type Messenger.
 */
@Getter
@Setter
public class Messenger {
    private MailServer mailServer;
    private TemplateEngine templateEngine;

    private static Client client = new Client("oleksii_voronin2@epam.com");

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
        StringBuilder input = new StringBuilder();
        if (args.length == 2) {
            mailServer = new FileMailServer();
            input = null;
        } else if (args.length == 0) {
            Scanner scanner = new Scanner(System.in);
            while(scanner.hasNext()) input.append(scanner.nextLine()).append("\n");
            mailServer = new ConsoleMailServer();
        } else {
            System.out.println("Incorrect number of arguments");
            return;
        }
        try {
            messenger = new Messenger(mailServer, templateEngine);
            messenger.sendMessage(client, templateEngine.createTemplate(input.toString()));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}