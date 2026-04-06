package com.tcc.underapp_api.modules.user.dto.request;

import com.tcc.underapp_api.common.records.ValidationMessages;
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

        @Size(min = 2, max = 100, message = ValidationMessages.FIRST_NAME_RANGE)
        @Pattern(
                regexp = "^[A-Za-zÀ-ÿ ]+$",
                message = ValidationMessages.FIRST_NAME_PATTERN
        )
        String firstName,

        @Size(min = 2, max = 100, message = ValidationMessages.LAST_NAME_RANGE)
        @Pattern(
                regexp = "^[A-Za-zÀ-ÿ ]+$",
                message = ValidationMessages.LAST_NAME_PATTERN
        )
        String lastName,

        @Pattern(
                regexp = "\\d{8}",
                message = ValidationMessages.CEP_PATTERN
        )
        String cep
) {}
