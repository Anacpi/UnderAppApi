package com.tcc.underapp_api.common.errors;

/**
 * Exception thrown when a requested resource is not found.
 * Used to indicate that an entity or resource does not exist in the system.
 */
public class NotFoundException extends AppException {
    
    /**
     * Constructs a new NotFoundException with the specified detail message.
     *
     * @param message the detail message describing why the resource was not found
     */
    public NotFoundException(String message) {
        super(message);
    }

    /**
     * Constructs a new NotFoundException with a technical and user-facing message.
     *
     * @param message the technical message
     * @param userMessage the message intended for the frontend
     */
    public NotFoundException(String message, String userMessage) {
        super(message, userMessage);
    }

    /**
     * Constructs a new NotFoundException with the specified detail message and cause.
     *
     * @param message the detail message describing why the resource was not found
     * @param cause the cause of the exception
     */
    public NotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new NotFoundException with the specified cause.
     *
     * @param cause the cause of the exception
     */
    public NotFoundException(Throwable cause) {
        super(cause);
    }
}
