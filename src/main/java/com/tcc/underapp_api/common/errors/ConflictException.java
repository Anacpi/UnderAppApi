package com.tcc.underapp_api.common.errors;

/**
 * Exception thrown when a conflict occurs, such as a resource already exists.
 * Used to indicate that an entity or resource already exists in the system.
 */
public class ConflictException extends AppException {

    /**
     * Constructs a new ConflictException with the specified detail message.
     *
     * @param message the detail message describing why the conflict occurred
     */
    public ConflictException(String message) {
        super(message);
    }

    /**
     * Constructs a new ConflictException with a technical and user-facing message.
     *
     * @param message the technical message
     * @param userMessage the message intended for the frontend
     */
    public ConflictException(String message, String userMessage) {
        super(message, userMessage);
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
