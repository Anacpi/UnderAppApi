package com.tcc.underapp_api.modules.auth.dto.Request;

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

        @NotBlank(message = "Email is required")
        @Email(message = "Invalid email address")
        @Size(max = 254, message = "Email must have at most 254 characters")
        String email,

        @NotBlank(message = "Password is required")
        @Size(max = 72, message = "Password must have at most 72 characters")
        String password
) {}
