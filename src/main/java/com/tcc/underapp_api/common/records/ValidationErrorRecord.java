package com.tcc.underapp_api.common.records;

/**
 * Record representing a validation error.
 *
 * @param field the field that caused the validation error
 * @param message the error message
 */
public record ValidationErrorRecord(String field, String message) {}
