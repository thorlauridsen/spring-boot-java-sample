package com.github.thorlauridsen.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.thorlauridsen.model.Customer;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.UUID;

/**
 * Data transfer object for customer.
 * This is created to ensure that FasterXML Jackson can serialize and deserialize customers.
 * Represents a customer with an id and an email.
 *
 * @param id   UUID of the customer.
 * @param mail Mail as string of the customer.
 */
@Schema(
        description = "Data transfer object for a customer",
        example = """
                {
                    "id": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
                    "mail": "bob@gmail.com"
                }
                """
)
public record CustomerDto(
        @JsonProperty("id") UUID id,
        @JsonProperty("mail") String mail
) {

    /**
     * Static method to convert an {@link Customer} model to an {@link CustomerDto}.
     *
     * @param customer {@link Customer} to convert.
     * @return {@link CustomerDto}.
     */
    public static CustomerDto fromModel(Customer customer) {
        return new CustomerDto(customer.id(), customer.mail());
    }
}
