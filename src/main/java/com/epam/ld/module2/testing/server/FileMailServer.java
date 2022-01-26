package com.epam.ld.module2.testing.server;

import com.epam.ld.module2.testing.service.FileService;

import java.io.IOException;

public class FileMailServer implements MailServer {

    @Override
    public void send(String address, String messageContent) throws IOException {
        System.out.println("Message sent to file");
        FileService.writeToFile(messageContent);
    }
}
