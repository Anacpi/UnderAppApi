package com.tcc.underapp_api.common.errors;

/**
 * Exception thrown when a client error occurs, such as a validation error.
 * Used to indicate that a client error occurred in the system.
 */
public class ClientException extends AppException {

    /**
     * Constructs a new ClientException with the specified detail message.
     *
     * @param message the detail message describing why the client exception occurred
     */
    public ClientException(String message) {
        super(message);
    }

    /**
     * Constructs a new ClientException with a technical and user-facing message.
     *
     * @param message the technical message
     * @param userMessage the message intended for the frontend
     */
    public ClientException(String message, String userMessage) {
        super(message, userMessage);
    }

    /**
     * Constructs a new ClientException with a technical and user-facing message and cause.
     *
     * @param message the technical message
     * @param userMessage the message intended for the frontend
     * @param cause the cause of the client exception
     */
    public ClientException(String message, String userMessage, Throwable cause) {
        super(message, userMessage, cause);
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
