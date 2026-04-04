package com.tcc.underapp_api.modules.auth.dto.Request;

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

            @NotBlank(message = "Email is required")
            @Email(message = "Invalid email address")
            @Size(max = 254, message = "Email must have at most 254 characters")
            String email,

            @NotBlank(message = "Password is required")
            @Size(min = 8, max = 72, message = "Password must be between 8 and 72 characters")
            @Pattern(
                    regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d).*$",
                    message = "Password must contain at least one uppercase letter, one lowercase letter, and one number"
            )
            String password,

            @NotBlank(message = "First name is required")
            @Size(min = 2, max = 100, message = "First name must be between 2 and 100 characters")
            @Pattern(
                    regexp = "^[A-Za-zÀ-ÿ ]+$",
                    message = "First name must contain only letters"
            )
            String firstName,

            @NotBlank(message = "Last name is required")
            @Size(min = 2, max = 100, message = "Last name must be between 2 and 100 characters")
            @Pattern(
                    regexp = "^[A-Za-zÀ-ÿ ]+$",
                    message = "Last name must contain only letters"
            )
            String lastName,

            @NotBlank(message = "CEP is required")
            @Pattern(
                    regexp = "\\d{8}",
                    message = "CEP must contain exactly 8 digits"
            )
            String cep
) {}
