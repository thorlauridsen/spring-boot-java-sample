package com.github.thorlauridsen.persistence;

import com.github.thorlauridsen.model.Customer;
import com.github.thorlauridsen.model.CustomerInput;
import java.util.Optional;
import java.util.UUID;

/**
 * Customer repository interface.
 * This is an interface containing methods for interacting with the customer table.
 * A repository class will implement this interface to provide the actual implementation.
 * This interface makes it easier to swap out the implementation of the repository if needed.
 */
public interface ICustomerRepo {

    /**
     * Save a customer in the database.
     *
     * @param customerInput Input object for creating a customer.
     * @return {@link Customer} model class.
     */
    Customer save(CustomerInput customerInput);

    /**
     * Find a customer by id.
     *
     * @param id UUID of the customer.
     * @return {@link Optional} of {@link Customer}.
     */
    Optional<Customer> findById(UUID id);
}
