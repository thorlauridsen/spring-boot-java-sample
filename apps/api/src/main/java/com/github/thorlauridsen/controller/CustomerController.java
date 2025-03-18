package com.github.thorlauridsen.controller;

import com.github.thorlauridsen.Customer;
import com.github.thorlauridsen.dto.CustomerDto;
import com.github.thorlauridsen.dto.CustomerInputDto;
import com.github.thorlauridsen.service.CustomerService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import java.net.URI;
import java.util.UUID;

import static com.github.thorlauridsen.controller.Endpoint.CUSTOMER_BASE_ENDPOINT;

/**
 * Customer controller class.
 * This class is a controller for the customer model.
 */
@Controller
public class CustomerController implements ICustomerController {

    private final CustomerService customerService;

    /**
     * Constructor for customer controller.
     *
     * @param customerService Customer service.
     */
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    /**
     * Save method for customer.
     * Creates the location for the newly created customer.
     * Return location and Customer.
     *
     * @param customerInput Input object for creating a customer.
     * @return ResponseEntity of created customer.
     */
    @Override
    public ResponseEntity<CustomerDto> save(CustomerInputDto customerInput) {
        Customer customer = customerService.save(customerInput.toModel());
        URI location = URI.create(CUSTOMER_BASE_ENDPOINT + "/" + customer.getId());

        return ResponseEntity.created(location).body(toDto(customer));
    }

    /**
     * Get method for customer.
     * This method returns a customer.
     *
     * @return ResponseEntity of customer.
     */
    @Override
    public ResponseEntity<CustomerDto> get(UUID id) {
        Customer customer = customerService.findById(id);
        return ResponseEntity.ok(toDto(customer));
    }

    /**
     * Convert Customer to CustomerDto.
     *
     * @param customer Customer to convert.
     * @return CustomerDto.
     */
    private CustomerDto toDto(Customer customer) {
        return new CustomerDto(customer.getId(), customer.getMail());
    }
}
