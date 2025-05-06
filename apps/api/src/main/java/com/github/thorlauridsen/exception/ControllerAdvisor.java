package com.github.thorlauridsen.exception;

import com.github.thorlauridsen.dto.ErrorDto;
import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Controller advisor for handling exceptions.
 * This ensures that whenever an exception is thrown, a proper error response is returned to the client.
 */
@ControllerAdvice
@Slf4j
public class ControllerAdvisor extends ResponseEntityExceptionHandler {

    /**
     * Handles all domain exceptions.
     * If any {@link DomainException} is thrown, this method will
     * catch it and return a response entity with an {@link ErrorDto}.
     * The returned HTTP status code will be derived from the specific {@link DomainException}.
     *
     * @param exception The domain exception to handle.
     * @return A response entity with an {@link ErrorDto}.
     */
    @ExceptionHandler(DomainException.class)
    public ResponseEntity<ErrorDto> handleDomainException(DomainException exception) {
        return error(exception, exception.getHttpStatus());
    }

    /**
     * Handles all exceptions.
     * If any exception is thrown, this method will catch it and return a response entity with an {@link ErrorDto}.
     * Returns an HTTP 500 status code if no domain exception is thrown.
     *
     * @param exception The exception to handle.
     * @return A response entity with an {@link ErrorDto}.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDto> handleEverything(Exception exception) {
        return error(exception, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException exception,
            @NonNull HttpHeaders headers,
            @NonNull HttpStatusCode status,
            @NonNull WebRequest request
    ) {
        val fieldErrors = exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .collect(Collectors.toMap(
                        FieldError::getField,
                        fe -> fe.getDefaultMessage() != null ? fe.getDefaultMessage() : "Invalid value"
                ));

        val errorDto = new ErrorDto(
                "Validation failed",
                OffsetDateTime.now(),
                fieldErrors
        );

        log.error("Validation failed: {}", fieldErrors, exception);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(errorDto);
    }

    /**
     * Log exception and return a response entity with an {@link ErrorDto}.
     *
     * @param exception  {@link Exception}.
     * @param httpStatus {@link HttpStatus}.
     */
    private ResponseEntity<ErrorDto> error(Exception exception, HttpStatus httpStatus) {
        val message = exception.getMessage() != null ? exception.getMessage() : "An unexpected error occurred";
        val errorDto = new ErrorDto(message, OffsetDateTime.now(), new HashMap<>());

        log.error(message, exception);
        return ResponseEntity.status(httpStatus).body(errorDto);
    }
}
