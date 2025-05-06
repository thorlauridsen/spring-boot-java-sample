package com.github.thorlauridsen.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Customer entity class.
 * Represents a customer with an id and an email.
 */
@Entity
@Getter
@NoArgsConstructor
@Table(name = "customer")
public class CustomerEntity {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false, unique = true)
    private String mail;

    /**
     * Constructor for customer.
     *
     * @param mail Mail as string of the customer.
     */
    public CustomerEntity(String mail) {
        this.mail = mail;
    }
}
