package com.github.thorlauridsen.controller;

import com.github.thorlauridsen.dto.CustomerDto;
import com.github.thorlauridsen.dto.CustomerInputDto;
import com.github.thorlauridsen.exception.CustomerNotFoundException;
import com.github.thorlauridsen.service.CustomerService;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import static com.github.thorlauridsen.controller.BaseEndpoint.CUSTOMER_BASE_ENDPOINT;

/**
 * Customer controller class.
 * This class implements the {@link ICustomerController} interface and
 * overrides the methods defined in the interface with implementations.
 * The controller is responsible for converting data transfer objects to models and vice versa.
 */
@RestController
@RequiredArgsConstructor
public class CustomerController implements ICustomerController {

    private final CustomerService customerService;

    /**
     * Save method for customer.
     * Creates the URI location for the newly created customer.
     * Return URI location and customer.
     *
     * @param customerInput Input object for creating a customer.
     * @return {@link ResponseEntity} with URI location and {@link CustomerDto}.
     */
    @Override
    public ResponseEntity<CustomerDto> save(@Valid CustomerInputDto customerInput) {
        val customer = customerService.save(customerInput.toModel());
        val location = URI.create(CUSTOMER_BASE_ENDPOINT + "/" + customer.id());

        return ResponseEntity.created(location).body(CustomerDto.fromModel(customer));
    }

    /**
     * Get a customer given an id.
     * This method will convert the model to a DTO and return it.
     *
     * @param id UUID of the customer to retrieve.
     * @return {@link ResponseEntity} with {@link CustomerDto}.
     * @throws CustomerNotFoundException if the customer is not found.
     */
    @Override
    public ResponseEntity<CustomerDto> get(UUID id) throws CustomerNotFoundException {
        val customer = customerService.findById(id);
        return ResponseEntity.ok(CustomerDto.fromModel(customer));
    }
}
