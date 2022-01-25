package com.epam.ld.module2.testing.server;

/**
 * Mail server class.
 */
public interface MailServer {

    /**
     * Send notification.
     *
     * @param address  the addresses
     * @param messageContent the message content
     */
    void send(String address, String messageContent);
}
