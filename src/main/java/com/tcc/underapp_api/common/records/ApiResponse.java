package com.tcc.underapp_api.common.records;

/**
 * Response record for API endpoints.
 * Contains the message and data of the response.
 *
 * @param message the message of the response
 * @param data the data of the response
 */
public record ApiResponse<T>(String message, T data) {
    /**
     * Creates a success response.
     *
     * @param data the data of the response
     * @return the success response
     */
    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>("success", data);
    }

    /**
     * Creates an error response.
     *
     * @param data the data of the response
     * @return the error response
     */
    public static <T> ApiResponse<T> error(T data) {
        return new ApiResponse<>("error", data);
    }
}