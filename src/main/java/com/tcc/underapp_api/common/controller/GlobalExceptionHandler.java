package com.tcc.underapp_api.common.controller;

import com.tcc.underapp_api.common.errors.ClientException;
import com.tcc.underapp_api.common.errors.ConflictException;
import com.tcc.underapp_api.common.errors.NotFoundException;
import com.tcc.underapp_api.common.errors.UnauthorizedExcepition;
import com.tcc.underapp_api.common.records.ApiResponse;
import com.tcc.underapp_api.common.records.ValidationErrorRecord;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import tools.jackson.databind.exc.InvalidFormatException;

import java.util.List;

/**
 * Global exception handler for REST endpoints.
 * Converts application and validation exceptions into standardized API responses.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles validation errors from request body validation.
     * Converts validation errors into a structured error response.
     *
     * @param ex the validation exception containing field errors
     * @return a ResponseEntity with validation error details and 400 Bad Request
     *         status
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<List<ValidationErrorRecord>>> handleValidationErrors(
            MethodArgumentNotValidException ex) {
        List<ValidationErrorRecord> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> new ValidationErrorRecord(error.getField(), error.getDefaultMessage()))
                .toList();

        return ResponseEntity.badRequest().body(new ApiResponse<>("validation error", errors));
    }

    /**
     * Handles type mismatch errors during JSON deserialization (e.g., String instead of Integer).
     * Converts them into a structured validation error response.
     *
     * @param ex the exception thrown when the HTTP message cannot be read
     * @return a ResponseEntity with validation error details and 400 Bad Request status
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiResponse<List<ValidationErrorRecord>>> handleHttpMessageNotReadable(
            HttpMessageNotReadableException ex) {

        if (ex.getCause() instanceof InvalidFormatException formatException) {
            String fieldName = "unknown";
            if (!formatException.getPath().isEmpty()) {
                var reference = formatException.getPath().getFirst();
                fieldName = reference.getPropertyName() != null
                        ? reference.getPropertyName()
                        : String.valueOf(reference.getIndex());
            }
            String expectedType = formatException.getTargetType().getSimpleName();
            String message = String.format("The field '%s' must be of type %s.", fieldName, expectedType);

            ValidationErrorRecord error = new ValidationErrorRecord(fieldName, message);
            return ResponseEntity.badRequest().body(new ApiResponse<>("validation error", List.of(error)));
        }

        ValidationErrorRecord error = new ValidationErrorRecord("body", "Invalid or malformed request body.");
        return ResponseEntity.badRequest().body(new ApiResponse<>("validation error", List.of(error)));
    }

    /**
     * Handles conflict exceptions.
     * Converts conflict exceptions into a structured error response.
     *
     * @param ex the conflict exception
     * @return a ResponseEntity with the error response and 409 Conflict status
     */
    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ApiResponse<String>> handleConflictException(ConflictException ex) {
        return ResponseEntity.status(409).body(ApiResponse.error(ex.getMessage()));
    }

    /**
     * Handles client exceptions.
     * Converts client exceptions into a structured error response.
     *
     * @param ex the client exception
     * @return a ResponseEntity with the error response and 400 Bad Request status
     */
    @ExceptionHandler(ClientException.class)
    public ResponseEntity<ApiResponse<String>> handleClientException(ClientException ex) {
        return ResponseEntity.badRequest().body(ApiResponse.error(ex.getMessage()));
    }

    /**
     * Handles not found exceptions.
     * Converts them into a structured error response.
     *
     * @param ex the not found exception
     * @return a ResponseEntity with the error response and 404 Not Found status
     */
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ApiResponse<String>> handleNotFoundException(NotFoundException ex) {
        return ResponseEntity.status(404).body(ApiResponse.error(ex.getMessage()));
    }

    /**
     * Handles unauthorized exceptions.
     * Converts unauthorized exceptions into a structured error response.
     *
     * @param ex the unauthorized exception
     * @return a ResponseEntity with the error response and 401 Unauthorized status
     */
    @ExceptionHandler(UnauthorizedExcepition.class)
    public ResponseEntity<ApiResponse<String>> handleUnauthorized(UnauthorizedExcepition ex) {
        return ResponseEntity.status(401).body(ApiResponse.error(ex.getMessage()));
    }

    /**
     * Handles unexpected runtime exceptions not mapped to a more specific handler.
     *
     * @param ex the runtime exception
     * @return a ResponseEntity with the error response and 500 Internal Server Error status
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiResponse<String>> handleRuntimeException(RuntimeException ex) {
        return ResponseEntity.status(500).body(ApiResponse.error(ex.getMessage()));
    }
}
