package com.github.thorlauridsen;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

/**
 * Customer repository interface.
 * This is a JPA repository for the customer entity.
 * It extends the JpaRepository interface which allows us to easily define CRUD methods.
 */
public interface CustomerRepo extends JpaRepository<CustomerEntity, UUID> {

    /**
     * Find a customer by id.
     *
     * @param id UUID of the customer.
     * @return Optional of customer entity.
     */
    Optional<CustomerEntity> findById(UUID id);
}
