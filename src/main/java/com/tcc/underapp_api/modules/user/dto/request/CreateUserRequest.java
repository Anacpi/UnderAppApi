package com.tcc.underapp_api.modules.user.dto.request;

/**
 * Internal DTO used to create users in the service layer.
 *
 * @param email the user email
 * @param password the raw password
 * @param firstName the user's first name
 * @param lastName the user's last name
 * @param cep the user's postal code
 */
public record CreateUserRequest(
        String email,
        String password,
        String firstName,
        String lastName,
        String cep
) {}
