package com.tcc.underapp_api.modules.auth.dto.Request;

import com.tcc.underapp_api.common.records.ValidationMessages;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * Request record used for user registration.
 *
 * @param email the user email
 * @param password the raw password informed during registration
 * @param firstName the user's first name
 * @param lastName the user's last name
 * @param cep the user's postal code
 */
public record RegisterRequest(

        @NotBlank(message = ValidationMessages.EMAIL_REQUIRED)
        @Email(message = ValidationMessages.INVALID_EMAIL)
        @Size(max = 254, message = ValidationMessages.EMAIL_MAX_LENGTH)
        String email,

        @NotBlank(message = ValidationMessages.PASSWORD_REQUIRED)
        @Size(min = 8, max = 72, message = ValidationMessages.PASSWORD_RANGE)
        @Pattern(
                regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d).*$",
                message = ValidationMessages.PASSWORD_PATTERN
        )
        String password,

        @NotBlank(message = ValidationMessages.FIRST_NAME_REQUIRED)
        @Size(min = 2, max = 100, message = ValidationMessages.FIRST_NAME_RANGE)
        @Pattern(
                regexp = "^[A-Za-zÀ-ÿ ]+$",
                message = ValidationMessages.FIRST_NAME_PATTERN
        )
        String firstName,

        @NotBlank(message = ValidationMessages.LAST_NAME_REQUIRED)
        @Size(min = 2, max = 100, message = ValidationMessages.LAST_NAME_RANGE)
        @Pattern(
                regexp = "^[A-Za-zÀ-ÿ ]+$",
                message = ValidationMessages.LAST_NAME_PATTERN
        )
        String lastName,

        @NotBlank(message = ValidationMessages.CEP_REQUIRED)
        @Pattern(
                regexp = "\\d{8}",
                message = ValidationMessages.CEP_PATTERN
        )
        String cep
) {}
