package com.github.thorlauridsen.persistence;

import com.github.thorlauridsen.model.Customer;
import com.github.thorlauridsen.model.CustomerInput;
import com.github.thorlauridsen.model.ICustomerRepo;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class CustomerRepo implements ICustomerRepo {

    private final CustomerJpaRepo jpaRepo;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * Constructor for customer repository facade.
     *
     * @param jpaRepo {@link CustomerJpaRepo} for interacting directly with the customer table.
     */
    public CustomerRepo(CustomerJpaRepo jpaRepo) {
        this.jpaRepo = jpaRepo;
    }

    /**
     * Save a customer.
     * This will create a new {@link CustomerEntity} and save it to the database.
     *
     * @param customerInput Input object for creating a customer.
     * @return {@link Customer} model class.
     */
    @Override
    public Customer save(CustomerInput customerInput) {
        logger.info("Saving customer with mail: {}", customerInput.mail());

        var customer = new CustomerEntity(customerInput.mail());
        var createdCustomer = jpaRepo.save(customer);
        logger.info("Customer saved with id: {}", createdCustomer.getId());

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
        logger.info("Finding customer with id: {}", id);
        var customer = jpaRepo.findById(id);

        if (customer.isPresent()) {
            logger.info("Found customer with id: {}", id);
        } else {
            logger.info("Customer not found with id: {}", id);
        }

        return customer.map(customerEntity -> new Customer(
                customerEntity.getId(),
                customerEntity.getMail()
        ));
    }
}
