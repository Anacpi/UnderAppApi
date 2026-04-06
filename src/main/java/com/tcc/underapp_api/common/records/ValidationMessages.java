package com.tcc.underapp_api.common.records;

/**
 * Technical validation messages used by Bean Validation annotations.
 */
public final class ValidationMessages {

    public static final String EMAIL_REQUIRED = "Email is required";
    public static final String INVALID_EMAIL = "Invalid email address";
    public static final String EMAIL_MAX_LENGTH = "Email must have at most 254 characters";
    public static final String PASSWORD_REQUIRED = "Password is required";
    public static final String PASSWORD_MAX_LENGTH = "Password must have at most 72 characters";
    public static final String PASSWORD_RANGE = "Password must be between 8 and 72 characters";
    public static final String PASSWORD_PATTERN =
            "Password must contain at least one uppercase letter, one lowercase letter, and one number";
    public static final String FIRST_NAME_REQUIRED = "First name is required";
    public static final String FIRST_NAME_RANGE = "First name must be between 2 and 100 characters";
    public static final String FIRST_NAME_PATTERN = "First name must contain only letters";
    public static final String LAST_NAME_REQUIRED = "Last name is required";
    public static final String LAST_NAME_RANGE = "Last name must be between 2 and 100 characters";
    public static final String LAST_NAME_PATTERN = "Last name must contain only letters";
    public static final String CEP_REQUIRED = "CEP is required";
    public static final String CEP_PATTERN = "CEP must contain exactly 8 digits";
    public static final String INVALID_BODY = "Invalid or malformed request body.";

    private ValidationMessages() {
    }
}
