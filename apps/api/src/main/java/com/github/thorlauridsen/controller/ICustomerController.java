package com.github.thorlauridsen.controller;

import com.github.thorlauridsen.Customer;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

public interface ICustomerController {

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
    ResponseEntity<Customer> get();
}
