package com.tcc.underapp_api.modules.user.dto.request;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * Request record used to update mutable user profile fields.
 *
 * @param firstName the updated first name
 * @param lastName the updated last name
 * @param cep the updated postal code
 */
public record UpdateUserRequest(

        @Size(min = 2, max = 100, message = "First name must be between 2 and 100 characters")
        @Pattern(
                regexp = "^[A-Za-zÀ-ÿ ]+$",
                message = "First name must contain only letters"
        )
        String firstName,

        @Size(min = 2, max = 100, message = "Last name must be between 2 and 100 characters")
        @Pattern(
                regexp = "^[A-Za-zÀ-ÿ ]+$",
                message = "Last name must contain only letters"
        )
        String lastName,

        @Pattern(
                regexp = "\\d{8}",
                message = "CEP must contain exactly 8 digits"
        )
        String cep
) {}
