package com.tcc.underapp_api.utils;

/**
 * Utility class for String operations.
 */
public class StringUtils {

    /**
     * Normalizes an email by trimming spaces and converting to lowercase.
     *
     * @param email the email to normalize
     *
     * @return the normalized email or null if the input is null
     */
    public static String normalizeEmail(String email) {
        return email == null ? null : email.trim().toLowerCase();
    }
}