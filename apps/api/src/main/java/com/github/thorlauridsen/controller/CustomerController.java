package com.github.thorlauridsen.controller;

import com.github.thorlauridsen.Customer;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import java.util.UUID;

/**
 * Customer controller class.
 * This class is a controller for the customer model.
 */
@Controller
public class CustomerController implements ICustomerController {


    /**
     * Get method for customer.
     * This method returns a customer.
     *
     * @return ResponseEntity of customer.
     */
    @Override
    public ResponseEntity<Customer> get() {
        Customer customer = new Customer(
                UUID.randomUUID(),
                "bob@gmail.com"
        );
        return ResponseEntity.ok(customer);
    }
}
