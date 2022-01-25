package com.epam.ld.module2.testing;

public class ConsoleMailServer implements MailServer {

    @Override
    public void send(String addresses, String messageContent) {
        System.out.println(messageContent);
    }
}
