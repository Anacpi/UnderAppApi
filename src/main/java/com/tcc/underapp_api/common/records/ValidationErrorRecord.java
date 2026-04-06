package com.tcc.underapp_api.common.records;

/**
 * Record representing a validation error.
 *
 * @param field the field that caused the validation error
 * @param message the error message
 * @param userMessage the localized message for the frontend
 */
public record ValidationErrorRecord(String field, String message, String userMessage) {}
