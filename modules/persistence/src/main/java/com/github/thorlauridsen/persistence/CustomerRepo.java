package com.github.thorlauridsen.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * Customer repository interface.
 * This is a JPA repository for the customer entity.
 * It extends the {@link JpaRepository} interface which allows us to easily define CRUD methods.
 */
public interface CustomerRepo extends JpaRepository<CustomerEntity, UUID> {
}
