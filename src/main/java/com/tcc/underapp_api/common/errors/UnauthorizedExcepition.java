package com.tcc.underapp_api.common.errors;

/**
 * Exception thrown when a user is not authorized to access a resource.
 */
public class UnauthorizedExcepition extends RuntimeException {

    /**
     * Constructs a new UnauthorizedExcepition with the specified message.
     *
     * @param message the message to be displayed
     */
    public UnauthorizedExcepition(String message) {
        super(message);
    }

    /**
     * Constructs a new UnauthorizedExcepition with the specified message and cause.
     *
     * @param message the message to be displayed
     * @param cause the cause of the exception
     */
    public UnauthorizedExcepition(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new UnauthorizedExcepition with the specified cause.
     *
     * @param cause the cause of the exception
     */
    public UnauthorizedExcepition(Throwable cause) {
        super(cause);
    }
}