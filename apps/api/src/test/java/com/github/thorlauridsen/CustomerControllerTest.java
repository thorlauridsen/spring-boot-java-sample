package com.github.thorlauridsen;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.thorlauridsen.dto.CustomerDto;
import com.github.thorlauridsen.dto.CustomerInputDto;
import java.util.UUID;

import com.github.thorlauridsen.dto.ErrorDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;

import static com.github.thorlauridsen.controller.BaseEndpoint.CUSTOMER_BASE_ENDPOINT;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CustomerControllerTest extends BaseMockMvc {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    public CustomerControllerTest(MockMvc mockMvc) {
        super(mockMvc);
    }

    @Test
    public void getCustomer_randomId_returnsNotFound() throws Exception {
        var id = UUID.randomUUID();
        var response = mockGet(CUSTOMER_BASE_ENDPOINT + "/" + id);
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "alice@gmail.com",
            "bob@gmail.com"
    })
    public void postCustomer_getCustomer_success(String mail) throws Exception {
        var customer = new CustomerInputDto(mail);
        var json = objectMapper.writeValueAsString(customer);
        var response = mockPost(json, CUSTOMER_BASE_ENDPOINT);
        assertEquals(HttpStatus.CREATED.value(), response.getStatus());

        var responseJson = response.getContentAsString();
        var createdCustomer = objectMapper.readValue(responseJson, CustomerDto.class);
        assertCustomer(createdCustomer, mail);

        var response2 = mockGet(CUSTOMER_BASE_ENDPOINT + "/" + createdCustomer.id());
        assertEquals(HttpStatus.OK.value(), response2.getStatus());

        var responseJson2 = response2.getContentAsString();
        var fetchedCustomer = objectMapper.readValue(responseJson2, CustomerDto.class);
        assertCustomer(fetchedCustomer, mail);
    }

    @Test
    void postCustomer_blankEmail_returnsBadRequest() throws Exception {
        var customer = new CustomerInputDto("");
        var json = objectMapper.writeValueAsString(customer);
        var response = mockPost(json, CUSTOMER_BASE_ENDPOINT);

        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());

        var responseJson = response.getContentAsString();
        var error = objectMapper.readValue(responseJson, ErrorDto.class);

        assertEquals("Validation failed", error.description());
        assertTrue(error.fieldErrors().containsKey("mail"));
        assertEquals("Email is required", error.fieldErrors().get("mail"));
    }

    @Test
    void postCustomer_invalidEmailFormat_returnsBadRequest() throws Exception {
        var customer = new CustomerInputDto("invalid-email");
        var json = objectMapper.writeValueAsString(customer);
        var response = mockPost(json, CUSTOMER_BASE_ENDPOINT);

        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());

        var responseJson = response.getContentAsString();
        var error = objectMapper.readValue(responseJson, ErrorDto.class);

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
