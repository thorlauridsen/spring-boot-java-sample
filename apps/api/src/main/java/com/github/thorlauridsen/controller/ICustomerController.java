package com.github.thorlauridsen.controller;

import com.github.thorlauridsen.Customer;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import static com.github.thorlauridsen.controller.Endpoint.CUSTOMER_BASE_ENDPOINT;

@Tag(name = "Customer Controller", description = "API for managing customers")
@RequestMapping(CUSTOMER_BASE_ENDPOINT)
public interface ICustomerController {

    /**
     * Get method for customer.
     * This method returns a customer.
     *
     * @return ResponseEntity of customer.
     */
    @GetMapping
    @Operation(
            summary = "Retrieve a customer",
            description = "Retrieve a customer"
    )
    ResponseEntity<Customer> get();
}
