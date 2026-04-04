package com.tcc.underapp_api.common.errors;

/**
 * Exception thrown when a requested resource is not found.
 * Used to indicate that an entity or resource does not exist in the system.
 */
public class NotFoundException extends RuntimeException {
    
    /**
     * Constructs a new NotFoundException with the specified detail message.
     *
     * @param message the detail message describing why the resource was not found
     */
    public NotFoundException(String message) {
        super(message);
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

    /**
     * Constructs a new NotFoundException with the specified detail message, cause, and enable suppression and writable stack trace.
     *
     * @param message the detail message describing why the resource was not found
     * @param cause the cause of the exception
     * @param enableSuppression whether suppression is enabled
     * @param writableStackTrace whether the stack trace should be writable
     */
    public NotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
