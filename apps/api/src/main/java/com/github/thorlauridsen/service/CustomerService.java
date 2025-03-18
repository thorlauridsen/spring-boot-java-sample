package com.github.thorlauridsen.service;

import com.github.thorlauridsen.Customer;
import com.github.thorlauridsen.CustomerInput;
import com.github.thorlauridsen.CustomerRepoFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Customer service class.
 * <p>
 * It is annotated with @Service to allow Spring to automatically inject it where needed.
 * This class uses the CustomerRepoFacade to interact with the repository.
 * <p>
 * The service class knows nothing about data transfer objects or database entities.
 * It only knows about the model classes and here you can implement business logic.
 * The idea here is to keep the various layers separated.
 */
@Service
public class CustomerService {

    private final CustomerRepoFacade customerRepo;
    private final Logger logger = LoggerFactory.getLogger(CustomerService.class);

    /**
     * Constructor for customer service.
     *
     * @param customerRepo Customer repository facade.
     */
    public CustomerService(CustomerRepoFacade customerRepo) {
        this.customerRepo = customerRepo;
    }

    /**
     * Save a customer.
     *
     * @param customerInput Input object for creating a customer.
     * @return Customer model class.
     */
    public Customer save(CustomerInput customerInput) {
        logger.info("Saving customer with mail: {}", customerInput.mail());
        return customerRepo.save(customerInput);
    }

    /**
     * Find a customer by id.
     *
     * @param id UUID of the customer.
     * @return Optional of customer.
     */
    public Customer findById(UUID id) {
        logger.info("Finding customer with id: {}", id);

        var customer = customerRepo.findById(id);
        if (customer.isEmpty()) {
            throw new IllegalArgumentException("Customer not found");
        }
        logger.info("Found customer: {}", customer);
        return customer.get();
    }
}
