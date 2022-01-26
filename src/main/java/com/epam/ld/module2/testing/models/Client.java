package com.epam.ld.module2.testing.models;

import lombok.AllArgsConstructor;

/**
 * The type Client.
 */
@AllArgsConstructor
public class Client {
    private String addresses;

    /**
     * Gets addresses.
     *
     * @return the addresses
     */
    public String getAddresses() {
        return addresses;
    }

    /**
     * Sets addresses.
     *
     * @param addresses the addresses
     */
    public void setAddresses(String addresses) {
        this.addresses = addresses;
    }
}
