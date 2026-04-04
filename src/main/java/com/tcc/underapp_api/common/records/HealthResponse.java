package com.tcc.underapp_api.common.records;

/**
 * Response record for health check endpoints.
 *
 * @param status the application health status
 * @param responseTimeMs the endpoint response time in milliseconds
 */
public record HealthResponse(String status, long responseTimeMs) {
}
