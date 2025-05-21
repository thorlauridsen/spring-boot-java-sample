package com.github.thorlauridsen;

import com.github.thorlauridsen.model.CustomerInput;
import com.github.thorlauridsen.model.ICustomerRepo;
import java.util.UUID;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test class for testing the {@link ICustomerRepo}.
 * Spins up a Spring Boot context to exercise the repository.
 */
@SpringBootTest
class CustomerRepoTest {

    @Autowired
    private ICustomerRepo customerRepo;

    @ParameterizedTest
    @ValueSource(strings = {
            "alice@gmail.com",
            "bob@gmail.com"
    })
    void saveCustomer_getCustomer_success(String mail) {
        val customer = new CustomerInput(mail);

        val savedCustomer = customerRepo.save(customer);

        assertNotNull(savedCustomer);
        assertNotNull(savedCustomer.id());
        assertEquals(mail, savedCustomer.mail());

        val optionalCustomer = customerRepo.findById(savedCustomer.id());
        assertTrue(optionalCustomer.isPresent());

        val foundCustomer = optionalCustomer.get();
        assertNotNull(foundCustomer);
        assertEquals(savedCustomer.id(), foundCustomer.id());
        assertEquals(mail, foundCustomer.mail());
    }

    @Test
    void getCustomer_nonExistentId_returnsEmpty() {
        val id = UUID.randomUUID();
        val customer = customerRepo.findById(id);
        assertTrue(customer.isEmpty());
    }
}
