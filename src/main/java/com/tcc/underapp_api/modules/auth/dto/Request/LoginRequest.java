package com.tcc.underapp_api.modules.auth.dto.Request;

import com.tcc.underapp_api.common.records.ValidationMessages;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Request record used for user authentication.
 *
 * @param email the user email
 * @param password the user password
 */
public record LoginRequest(

        @NotBlank(message = ValidationMessages.EMAIL_REQUIRED)
        @Email(message = ValidationMessages.INVALID_EMAIL)
        @Size(max = 254, message = ValidationMessages.EMAIL_MAX_LENGTH)
        String email,

        @NotBlank(message = ValidationMessages.PASSWORD_REQUIRED)
        @Size(max = 72, message = ValidationMessages.PASSWORD_MAX_LENGTH)
        String password
) {}
