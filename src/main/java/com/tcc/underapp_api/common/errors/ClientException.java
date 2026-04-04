package com.tcc.underapp_api.common.errors;

/**
 * Exception thrown when a client error occurs, such as a validation error.
 * Used to indicate that a client error occurred in the system.
 */
public class ClientException extends RuntimeException {

    /**
     * Constructs a new ClientException with the specified detail message.
     *
     * @param message the detail message describing why the client exception occurred
     */
    public ClientException(String message) {
        super(message);
    }

    /**
     * Constructs a new ClientException with the specified detail message and detail message.
     *
     * @param message the detail message describing why the client exception occurred
     * @param detailMessage the detail message describing the client exception
     */
    public ClientException(String message, String detailMessage) {
        super(message + ": " + detailMessage);
    }

    /**
     * Constructs a new ClientException with the specified detail message and cause.
     *
     * @param message the detail message describing why the client exception occurred
     * @param cause the cause of the client exception
     */
    public ClientException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new ClientException with the specified cause.
     *
     * @param cause the cause of the client exception
     */
    public ClientException(Throwable cause) {
        super(cause);
    }
}