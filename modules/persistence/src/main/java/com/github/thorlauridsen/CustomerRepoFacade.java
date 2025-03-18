package com.github.thorlauridsen;

import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * Customer repository facade class.
 * <p>
 * This class is a facade for the customer repository.
 * A service class can use this facade to easily interact with the
 * repository without needing to know about the database entity model.
 * <p>
 * It is annotated with @Repository to allow Spring to automatically
 * detect it as a bean and inject it where needed.
 */
@Repository
public class CustomerRepoFacade {

    private final CustomerRepo customerRepo;

    /**
     * Constructor for customer repository wrapper.
     *
     * @param customerRepo Customer repository.
     */
    public CustomerRepoFacade(CustomerRepo customerRepo) {
        this.customerRepo = customerRepo;
    }

    /**
     * Save a customer.
     * This will create a new CustomerEntity and save it to the database.
     *
     * @param customerInput Input object for creating a customer.
     * @return Customer model class.
     */
    public Customer save(CustomerInput customerInput) {
        var customer = new CustomerEntity(customerInput.mail());
        var createdCustomer = customerRepo.save(customer);

        return new Customer(
                createdCustomer.getId(),
                createdCustomer.getMail()
        );
    }

    /**
     * Find a customer by id.
     * This will handle converting the CustomerEntity to a Customer model.
     *
     * @param id UUID of the customer.
     * @return Optional of customer.
     */
    public Optional<Customer> findById(UUID id) {
        var customer = customerRepo.findById(id);

        return customer.map(customerEntity -> new Customer(
                customerEntity.getId(),
                customerEntity.getMail()
        ));
    }
}
