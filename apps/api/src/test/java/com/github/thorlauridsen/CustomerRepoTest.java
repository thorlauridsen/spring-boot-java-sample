package com.github.thorlauridsen;

import com.github.thorlauridsen.model.CustomerInput;
import com.github.thorlauridsen.model.ICustomerRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test class for testing the {@link ICustomerRepo}.
 * Spins up a Spring Boot context to exercise the repository.
 */
@SpringBootTest
public class CustomerRepoTest {

    @Autowired
    private ICustomerRepo customerRepo;

    @ParameterizedTest
    @ValueSource(strings = {
            "alice@gmail.com",
            "bob@gmail.com"
    })
    public void saveCustomer_getCustomer_success(String mail) {
        var customer = new CustomerInput(mail);

        var savedCustomer = customerRepo.save(customer);

        assertNotNull(savedCustomer);
        assertNotNull(savedCustomer.id());
        assertEquals(mail, savedCustomer.mail());

        var optionalCustomer = customerRepo.findById(savedCustomer.id());
        assertTrue(optionalCustomer.isPresent());

        var foundCustomer = optionalCustomer.get();
        assertNotNull(foundCustomer);
        assertEquals(savedCustomer.id(), foundCustomer.id());
        assertEquals(mail, foundCustomer.mail());
    }

    @Test
    public void getCustomer_nonExistentId_returnsEmpty() {
        var id = UUID.randomUUID();
        var customer = customerRepo.findById(id);
        assertTrue(customer.isEmpty());
    }
}
