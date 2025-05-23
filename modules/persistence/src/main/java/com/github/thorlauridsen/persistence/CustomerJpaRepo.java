package com.github.thorlauridsen.persistence;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Customer repository interface.
 * This is a JPA repository for the {@link CustomerEntity}.
 * It extends the {@link JpaRepository} interface which allows us to easily define CRUD methods.
 */
public interface CustomerJpaRepo extends JpaRepository<CustomerEntity, UUID> {
}
