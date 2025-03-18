package com.github.thorlauridsen.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.thorlauridsen.CustomerInput;

/**
 * Data transfer object for creating a customer.
 * This is created to ensure that FasterXML Jackson can serialize and deserialize customers.
 * Contains all the fields for creating a customer.
 * This class has final fields for immutability.
 */
public class CustomerInputDto {
    private final String mail;

    /**
     * Constructor for customer.
     *
     * @param mail Mail as string of the customer.
     */
    @JsonCreator
    public CustomerInputDto(@JsonProperty("mail") String mail) {
        this.mail = mail;
    }

    /**
     * Convert to model.
     *
     * @return CustomerInput model.
     */
    public CustomerInput toModel() {
        return new CustomerInput(mail);
    }
}
