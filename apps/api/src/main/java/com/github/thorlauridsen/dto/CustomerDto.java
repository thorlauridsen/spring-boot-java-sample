package com.github.thorlauridsen.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

/**
 * Data transfer object for customer.
 * This is created to ensure that FasterXML Jackson can serialize and deserialize customers.
 * Represents a customer with an id and an email.
 * This class has final fields for immutability.
 */
public class CustomerDto {
    private final UUID id;
    private final String mail;

    /**
     * Constructor for customer.
     *
     * @param id   UUID of the customer.
     * @param mail Mail as string of the customer.
     */
    @JsonCreator
    public CustomerDto(@JsonProperty("id") UUID id, @JsonProperty("mail") String mail) {
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
