package com.tcc.underapp_api.common.errors;

/**
 * Exception thrown when a conflict occurs, such as a resource already exists.
 * Used to indicate that an entity or resource already exists in the system.
 */
public class ConflictException extends RuntimeException {

    /**
     * Constructs a new ConflictException with the specified detail message.
     *
     * @param message the detail message describing why the conflict occurred
     */
    public ConflictException(String message) {
        super(message);
    }

    /**A
     * Constructs a new ConflictException with the specified detail message and cause.
     *
     * @param message the detail message describing why the conflict occurred
     * @param cause the cause of the conflict
     */
    public ConflictException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new ConflictException with the specified cause.
     *
     * @param cause the cause of the conflict
     */
    public ConflictException(Throwable cause) {
        super(cause);
    }
}
