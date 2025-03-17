package com.github.thorlauridsen;

import java.util.UUID;

/**
 * Customer model class.
 * Represents a customer with an id and an email.
 * This class has final fields for immutability.
 */
public class Customer {
    private final UUID id;
    private final String mail;

    /**
     * Constructor for customer.
     *
     * @param id   UUID of the customer.
     * @param mail Mail as string of the customer.
     */
    public Customer(UUID id, String mail) {
        this.id = id;
        this.mail = mail;
    }

    /**
     * Getter for id.
     *
     * @return UUID of the customer.
     */
    public UUID getId() {
        return id;
    }

    /**
     * Getter for mail.
     *
     * @return mail as string of the customer.
     */
    public String getMail() {
        return mail;
    }
}
