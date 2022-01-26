package com.epam.ld.module2.testing.service;

import com.epam.ld.module2.testing.Messenger;
import com.epam.ld.module2.testing.models.Client;
import com.epam.ld.module2.testing.server.ConsoleMailServer;
import com.epam.ld.module2.testing.server.FileMailServer;
import com.epam.ld.module2.testing.server.MailServer;
import lombok.Getter;
import lombok.Setter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

@Getter
@Setter
public class ModeService {

    private Client client;
    private TemplateEngine templateEngine;
    private Messenger messenger;
    private MailServer mailServer;
    private StringBuilder input = new StringBuilder();

    public ModeService(Client client, TemplateEngine templateEngine) {
        this.client = client;
        this.templateEngine = templateEngine;
    }

    public void execute(String[] args) throws IOException {
        if (args.length == 2) {
            fileMode(args[0], args[1]);
        } else if (args.length == 0) {
            consoleMode();
        } else {
            System.out.println("Incorrect number of arguments");
            return;
        }

        messenger = new Messenger(mailServer, templateEngine);
        messenger.sendMessage(client, templateEngine.createTemplate(input.toString()));
    }

    public void fileMode(String fileInput, String fileOutput) throws FileNotFoundException {
        mailServer = new FileMailServer();
        input.append(FileService.readFromFile(fileInput));
        FileService.OUTPUT_FILE = fileOutput;
    }

    public void consoleMode() {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) input.append(scanner.nextLine()).append("\n");
        mailServer = new ConsoleMailServer();
    }
}
