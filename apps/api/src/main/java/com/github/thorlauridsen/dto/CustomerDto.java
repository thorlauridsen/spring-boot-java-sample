package com.github.thorlauridsen.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

/**
 * Data transfer object for customer.
 * This is created to ensure that FasterXML Jackson can serialize and deserialize customers.
 * Represents a customer with an id and an email.
 *
 * @param id   UUID of the customer.
 * @param mail Mail as string of the customer.
 */
public record CustomerDto(@JsonProperty("id") UUID id, @JsonProperty("mail") String mail) {
}