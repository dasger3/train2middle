package com.epam.ld.module2.testing.server;

public class ConsoleMailServer implements MailServer {

    @Override
    public void send(String address, String messageContent) {
        System.out.println("Mail sent to console");
        System.out.println(messageContent);
    }
}
