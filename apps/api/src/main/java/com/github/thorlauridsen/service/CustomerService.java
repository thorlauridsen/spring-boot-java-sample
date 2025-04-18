package com.github.thorlauridsen.service;

import com.github.thorlauridsen.exception.CustomerNotFoundException;
import com.github.thorlauridsen.model.Customer;
import com.github.thorlauridsen.model.CustomerInput;
import com.github.thorlauridsen.model.ICustomerRepo;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Customer service class.
 * <p>
 * It is annotated with {@link Service} to allow Spring to automatically inject it where needed.
 * This class uses the {@link ICustomerRepo} to interact with the repository.
 * <p>
 * The service class knows nothing about data transfer objects or database entities.
 * It only knows about the model classes and here you can implement business logic.
 * The idea here is to keep the various layers separated.
 */
@Service
public class CustomerService {

    private final ICustomerRepo customerRepo;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * Constructor for customer service.
     *
     * @param customerRepo {@link ICustomerRepo} for interacting with the database.
     */
    public CustomerService(ICustomerRepo customerRepo) {
        this.customerRepo = customerRepo;
    }

    /**
     * Save a customer.
     *
     * @param customerInput {@link CustomerInput} for creating a customer.
     * @return {@link Customer}.
     */
    public Customer save(CustomerInput customerInput) {
        logger.info("Saving customer with mail: {}", customerInput.mail());
        return customerRepo.save(customerInput);
    }

    /**
     * Find a customer by id.
     *
     * @param id UUID of the customer.
     * @return {@link Customer}.
     * @throws CustomerNotFoundException if the customer is not found.
     */
    public Customer findById(UUID id) throws CustomerNotFoundException {
        logger.info("Finding customer with id: {}", id);

        var customer = customerRepo.findById(id);
        if (customer.isEmpty()) {
            throw new CustomerNotFoundException("Customer not found with id: " + id);
        }
        logger.info("Found customer with id: {}", id);
        return customer.get();
    }
}
