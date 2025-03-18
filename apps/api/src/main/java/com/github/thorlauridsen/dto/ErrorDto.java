package com.github.thorlauridsen.dto;

import java.time.OffsetDateTime;

/**
 * Data transfer object for handling errors.
 * Contains the description of the error and the time it occurred.
 *
 * @param description Description of the error.
 * @param time        {@link OffsetDateTime} when the error occurred.
 */
public record ErrorDto(
        String description,
        OffsetDateTime time
) {
}
