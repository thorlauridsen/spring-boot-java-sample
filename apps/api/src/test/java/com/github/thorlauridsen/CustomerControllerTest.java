package com.github.thorlauridsen;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.thorlauridsen.dto.CustomerDto;
import com.github.thorlauridsen.dto.CustomerInputDto;
import com.github.thorlauridsen.dto.ErrorDto;
import java.util.UUID;
import lombok.val;
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
        val id = UUID.randomUUID();
        val response = mockGet(CUSTOMER_BASE_ENDPOINT + "/" + id);
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "alice@gmail.com",
            "bob@gmail.com"
    })
    public void postCustomer_getCustomer_success(String mail) throws Exception {
        val customer = new CustomerInputDto(mail);
        val json = objectMapper.writeValueAsString(customer);
        val response = mockPost(json, CUSTOMER_BASE_ENDPOINT);
        assertEquals(HttpStatus.CREATED.value(), response.getStatus());

        val responseJson = response.getContentAsString();
        val createdCustomer = objectMapper.readValue(responseJson, CustomerDto.class);
        assertCustomer(createdCustomer, mail);

        val response2 = mockGet(CUSTOMER_BASE_ENDPOINT + "/" + createdCustomer.id());
        assertEquals(HttpStatus.OK.value(), response2.getStatus());

        val responseJson2 = response2.getContentAsString();
        val fetchedCustomer = objectMapper.readValue(responseJson2, CustomerDto.class);
        assertCustomer(fetchedCustomer, mail);
    }

    @Test
    void postCustomer_blankEmail_returnsBadRequest() throws Exception {
        val customer = new CustomerInputDto("");
        val json = objectMapper.writeValueAsString(customer);
        val response = mockPost(json, CUSTOMER_BASE_ENDPOINT);

        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());

        val responseJson = response.getContentAsString();
        val error = objectMapper.readValue(responseJson, ErrorDto.class);

        assertEquals("Validation failed", error.description());
        assertTrue(error.fieldErrors().containsKey("mail"));
        assertEquals("Email is required", error.fieldErrors().get("mail"));
    }

    @Test
    void postCustomer_invalidEmailFormat_returnsBadRequest() throws Exception {
        val customer = new CustomerInputDto("invalid-email");
        val json = objectMapper.writeValueAsString(customer);
        val response = mockPost(json, CUSTOMER_BASE_ENDPOINT);

        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());

        val responseJson = response.getContentAsString();
        val error = objectMapper.readValue(responseJson, ErrorDto.class);

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
