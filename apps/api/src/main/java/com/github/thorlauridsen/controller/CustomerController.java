package com.github.thorlauridsen.controller;

import com.github.thorlauridsen.Customer;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.UUID;

/**
 * Customer controller class.
 * This class is a controller for the customer model.
 */
@Controller
public class CustomerController {

    /**
     * Get method for customer.
     * This method returns a customer.
     *
     * @return ResponseEntity of customer.
     */
    @GetMapping("/customer")
    @Operation(
            summary = "Retrieve a customer",
            description = "Retrieve a customer"
    )
    public ResponseEntity<Customer> get() {
        Customer customer = new Customer(
                UUID.randomUUID(),
                "bob@gmail.com"
        );
        return ResponseEntity.ok(customer);
    }
}
