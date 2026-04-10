package com.tcc.underapp_api.common.errors;

/**
 * Base exception for application errors that need a frontend-facing message.
 */
public abstract class AppException extends RuntimeException {

    private final String userMessage;

    protected AppException(String message) {
        super(message);
        this.userMessage = null;
    }

    protected AppException(String message, Throwable cause) {
        super(message, cause);
        this.userMessage = null;
    }

    protected AppException(String message, String userMessage) {
        super(message);
        this.userMessage = userMessage;
    }

    protected AppException(String message, String userMessage, Throwable cause) {
        super(message, cause);
        this.userMessage = userMessage;
    }

    protected AppException(Throwable cause, String userMessage) {
        super(cause);
        this.userMessage = userMessage;
    }

    protected AppException(Throwable cause) {
        super(cause);
        this.userMessage = null;
    }

    public String getUserMessage() {
        return userMessage;
    }
}
