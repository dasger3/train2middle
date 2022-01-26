package com.epam.ld.module2.testing.server;

import com.epam.ld.module2.testing.service.FileService;

import java.io.IOException;

public class FileMailServer implements MailServer {



    @Override
    public void send(String address, String messageContent) throws IOException {
        System.out.println("Get here");
        FileService.writeToFile(messageContent);
    }
}
