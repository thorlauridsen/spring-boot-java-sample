package com.github.thorlauridsen.persistence;

import com.github.thorlauridsen.model.Customer;
import com.github.thorlauridsen.model.CustomerInput;
import com.github.thorlauridsen.model.ICustomerRepo;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Repository;

/**
 * Customer repository facade class.
 * <p>
 * This class is a facade for the {@link CustomerJpaRepo}.
 * A service class can use this facade to easily interact with the
 * repository without needing to know about the database entity {@link CustomerEntity}.
 * <p>
 * It is annotated with {@link Repository} to allow Spring to automatically
 * detect it as a bean and inject it where needed.
 */
@Repository
@RequiredArgsConstructor
@Slf4j
public class CustomerRepo implements ICustomerRepo {

    private final CustomerJpaRepo jpaRepo;

    /**
     * Save a customer.
     * This will create a new {@link CustomerEntity} and save it to the database.
     *
     * @param customerInput Input object for creating a customer.
     * @return {@link Customer} model class.
     */
    @Override
    public Customer save(CustomerInput customerInput) {
        log.info("Saving customer with mail: {}", customerInput.mail());

        val customer = new CustomerEntity(customerInput.mail());
        val createdCustomer = jpaRepo.save(customer);
        log.info("Customer saved with id: {}", createdCustomer.getId());

        return new Customer(
                createdCustomer.getId(),
                createdCustomer.getMail()
        );
    }

    /**
     * Find a customer by id.
     * This method will convert the {@link CustomerEntity} to a {@link Customer} model.
     *
     * @param id UUID of the customer.
     * @return {@link Optional} of {@link Customer}.
     */
    @Override
    public Optional<Customer> findById(UUID id) {
        log.info("Finding customer with id: {}", id);
        val customer = jpaRepo.findById(id);

        if (customer.isPresent()) {
            log.info("Found customer with id: {}", id);
        } else {
            log.info("Customer not found with id: {}", id);
        }

        return customer.map(customerEntity -> new Customer(
                customerEntity.getId(),
                customerEntity.getMail()
        ));
    }
}
