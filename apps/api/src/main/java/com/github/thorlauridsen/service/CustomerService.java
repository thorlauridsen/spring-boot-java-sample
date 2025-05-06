package com.github.thorlauridsen.service;

import com.github.thorlauridsen.exception.CustomerNotFoundException;
import com.github.thorlauridsen.model.Customer;
import com.github.thorlauridsen.model.CustomerInput;
import com.github.thorlauridsen.model.ICustomerRepo;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
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
@RequiredArgsConstructor
@Slf4j
public class CustomerService {

    private final ICustomerRepo customerRepo;

    /**
     * Save a customer.
     *
     * @param customerInput {@link CustomerInput} for creating a customer.
     * @return {@link Customer}.
     */
    public Customer save(CustomerInput customerInput) {
        log.info("Saving customer with mail: {}", customerInput.mail());
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
        log.info("Finding customer with id: {}", id);

        val customer = customerRepo.findById(id);
        if (customer.isEmpty()) {
            throw new CustomerNotFoundException("Customer not found with id: " + id);
        }
        log.info("Found customer with id: {}", id);
        return customer.get();
    }
}
