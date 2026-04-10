package com.tcc.underapp_api.common.records;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Response record for API endpoints.
 * Contains the message and data of the response.
 *
 * @param message the message of the response
 * @param userMessage the message intended for the frontend
 * @param data the data of the response
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ApiResponse<T>(String message, String userMessage, T data) {
    public ApiResponse(String message, T data) {
        this(message, null, data);
    }

    /**
     * Creates a success response.
     *
     * @param data the data of the response
     * @return the success response
     */
    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>("success", null, data);
    }

    /**
     * Creates a success response with a frontend-facing message.
     *
     * @param userMessage the message intended for the frontend
     * @param data the data of the response
     * @return the success response
     */
    public static <T> ApiResponse<T> success(String userMessage, T data) {
        return new ApiResponse<>("success", userMessage, data);
    }

    /**
     * Creates an error response.
     *
     * @param data the data of the response
     * @return the error response
     */
    public static <T> ApiResponse<T> error(T data) {
        return new ApiResponse<>("error", null, data);
    }

    /**
     * Creates an error response with a frontend-facing message.
     *
     * @param userMessage the message intended for the frontend
     * @param data the technical error payload
     * @return the error response
     */
    public static <T> ApiResponse<T> error(String userMessage, T data) {
        return new ApiResponse<>("error", userMessage, data);
    }
}
