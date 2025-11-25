package com.github.thorlauridsen;

import com.github.thorlauridsen.exception.CustomerNotFoundException;
import com.github.thorlauridsen.model.Customer;
import com.github.thorlauridsen.model.CustomerInput;
import com.github.thorlauridsen.persistence.CustomerRepo;
import com.github.thorlauridsen.service.CustomerService;
import java.util.UUID;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Test class for testing the {@link CustomerService}.
 * This class uses the @SpringBootTest annotation to spin up a Spring Boot instance.
 * This ensures that Spring can automatically inject {@link CustomerService} with a {@link CustomerRepo}
 */
@ActiveProfiles("postgres")
@SpringBootTest
@Testcontainers
class CustomerServiceTest {

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:18");

    @Autowired
    private CustomerService customerService;

    @Test
    void getCustomerWithRandomIdReturnsNotFound() {
        val id = UUID.randomUUID();
        assertThrows(CustomerNotFoundException.class, () -> customerService.findById(id));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "alice@gmail.com",
            "bob@gmail.com"
    })
    void saveCustomerAndGetCustomerSuccess(String mail) throws CustomerNotFoundException {
        val customer = new CustomerInput(mail);

        val savedCustomer = customerService.save(customer);
        assertCustomer(savedCustomer, mail);

        val fetchedCustomer = customerService.findById(savedCustomer.id());
        assertCustomer(fetchedCustomer, mail);
    }

    /**
     * Ensure that the customer is not null and that the id is not null.
     * Assert that the mail is equal to the expected mail.
     *
     * @param customer     {@link Customer}
     * @param expectedMail Expected mail of the customer.
     */
    private void assertCustomer(Customer customer, String expectedMail) {
        assertNotNull(customer);
        assertNotNull(customer.id());
        assertEquals(expectedMail, customer.mail());
    }
}
