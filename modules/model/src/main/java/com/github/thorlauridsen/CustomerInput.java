package com.github.thorlauridsen;

/**
 * Customer model class for creating a customer.
 * Contains all the fields for creating a customer.
 * This class has final fields for immutability.
 */
public class CustomerInput {
    private final String mail;

    /**
     * Constructor for customer.
     *
     * @param mail Mail as string of the customer.
     */
    public CustomerInput(String mail) {
        this.mail = mail;
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
