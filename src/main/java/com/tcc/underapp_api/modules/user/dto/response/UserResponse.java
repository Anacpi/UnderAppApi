package com.tcc.underapp_api.modules.user.dto.response;

import com.tcc.underapp_api.database.models.User;

/**
 * Record representing the data of a user for API responses.
 *
 * @param id the user's ID
 * @param email the user's email
 * @param firstName the user's first name
 * @param lastName the user's last name
 * @param cep the user's postal code
 */
public record UserResponse(
    Long id,
    String email,
    String firstName,
    String lastName,
    String cep,
    String profileImageUrl
) {
    /**
     * Creates a UserRecord from a User entity.
     *
     * @param user the User entity to convert
     * @return a new UserRecord with the entity's data
     */
    public static UserResponse fromEntity(User user, String profileImageUrl) {
        return new UserResponse(
            user.getId(),
            user.getEmail(),
            user.getFirstName(),
            user.getLastName(),
            user.getCep(),
            profileImageUrl
        );
    }
}
