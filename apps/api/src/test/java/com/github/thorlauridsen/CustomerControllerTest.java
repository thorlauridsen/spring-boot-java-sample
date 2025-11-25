package com.github.thorlauridsen;

import com.github.thorlauridsen.dto.CustomerDto;
import com.github.thorlauridsen.dto.CustomerInputDto;
import com.github.thorlauridsen.dto.ErrorDto;
import java.util.UUID;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import tools.jackson.databind.json.JsonMapper;

import static com.github.thorlauridsen.controller.BaseEndpoint.CUSTOMER_BASE_ENDPOINT;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test class for testing the CustomerController.
 * This class extends the BaseMockMvc class so this will spin up a Spring Boot instance for the tests.
 * A local Docker instance is required to run the tests as Testcontainers is used.
 */
@ActiveProfiles("postgres")
@Testcontainers
class CustomerControllerTest extends BaseControllerTest {

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:18");

    @Autowired
    private JsonMapper jsonMapper;

    @Test
    void getCustomer_randomId_returnsNotFound() {
        val id = UUID.randomUUID();
        val response = get(CUSTOMER_BASE_ENDPOINT + "/" + id);

        response.expectStatus().isEqualTo(HttpStatus.NOT_FOUND);
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "alice@gmail.com",
            "bob@gmail.com"
    })
    void postCustomer_getCustomer_success(String mail) {
        val customer = new CustomerInputDto(mail);
        val json = jsonMapper.writeValueAsString(customer);
        val response = post(CUSTOMER_BASE_ENDPOINT, json);
        response.expectStatus().isEqualTo(HttpStatus.CREATED);

        val createdCustomer = response.expectBody(CustomerDto.class).returnResult().getResponseBody();
        assertNotNull(createdCustomer);
        assertCustomer(createdCustomer, mail);

        val response2 = get(CUSTOMER_BASE_ENDPOINT + "/" + createdCustomer.id());
        response2.expectStatus().isEqualTo(HttpStatus.OK);

        val fetchedCustomer = response2.expectBody(CustomerDto.class).returnResult().getResponseBody();
        assertCustomer(fetchedCustomer, mail);
    }

    @Test
    void postCustomer_blankEmail_returnsBadRequest() {
        val customer = new CustomerInputDto("");
        val json = jsonMapper.writeValueAsString(customer);
        val response = post(CUSTOMER_BASE_ENDPOINT, json);

        response.expectStatus().isEqualTo(HttpStatus.BAD_REQUEST);
        val error = response.expectBody(ErrorDto.class).returnResult().getResponseBody();

        assertNotNull(error);
        assertEquals("Validation failed", error.description());
        assertTrue(error.fieldErrors().containsKey("mail"));
        assertEquals("Email is required", error.fieldErrors().get("mail"));
    }

    @Test
    void postCustomer_invalidEmailFormat_returnsBadRequest() {
        val customer = new CustomerInputDto("invalid-email");
        val json = jsonMapper.writeValueAsString(customer);
        val response = post(CUSTOMER_BASE_ENDPOINT, json);

        response.expectStatus().isEqualTo(HttpStatus.BAD_REQUEST);
        val error = response.expectBody(ErrorDto.class).returnResult().getResponseBody();

        assertNotNull(error);
        assertEquals("Validation failed", error.description());
        assertTrue(error.fieldErrors().containsKey("mail"));
        assertEquals("Invalid email format", error.fieldErrors().get("mail"));
    }

    /**
     * Ensure that the customer is not null and that the id is not null.
     * Assert that the mail is equal to the expected mail.
     *
     * @param customer     {@link CustomerDto}
     * @param expectedMail Expected mail of the customer.
     */
    private void assertCustomer(CustomerDto customer, String expectedMail) {
        assertNotNull(customer);
        assertNotNull(customer.id());
        assertEquals(expectedMail, customer.mail());
    }
}
