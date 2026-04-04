package com.tcc.underapp_api.modules.user.service;

import com.tcc.underapp_api.common.errors.ClientException;
import com.tcc.underapp_api.common.errors.ConflictException;
import com.tcc.underapp_api.common.errors.NotFoundException;
import com.tcc.underapp_api.database.models.User;
import com.tcc.underapp_api.database.repository.UserRepository;

import com.tcc.underapp_api.modules.user.dto.request.CreateUserRequest;
import com.tcc.underapp_api.modules.user.dto.request.UpdateUserRequest;
import jakarta.transaction.Transactional;

import lombok.AllArgsConstructor;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.tcc.underapp_api.utils.StringUtils.normalizeEmail;
import static org.hibernate.internal.util.StringHelper.isNotBlank;


/**
 * Service responsible for user management operations.
 * Handles retrieval, creation, update, and deletion of users.
 */
@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Gets a user by their identifier.
     *
     * @param id the user identifier
     * @return the found user
     * @throws NotFoundException if the user is not found
     */
    public User getById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found"));
    }

    /**
     * Gets a user by their email address.
     *
     * @param email the email address to search for
     * 
     * @return a UserRecord with the user's data
     * 
     * @throws NotFoundException if the user is not found
     */
    public User getByEmail(String email) {
        String normalizedEmail = normalizeEmail(email);
        return userRepository.findByEmail(normalizedEmail)
            .orElseThrow(() -> new NotFoundException("User not founad"));
    }

    /**
     * Creates a new user with the provided data
     * 
     * @param data the data to create the user from
     * 
     * @return the created user
     * 
     * @throws ConflictException if the email is already in use
     * @throws ClientException if an error occurs while creating the user
     */
    @Transactional
    public User createUser(CreateUserRequest data) {
        String email = normalizeEmail(data.email());

        this.validateUserExists(email);

        String encryptedPassword = passwordEncoder.encode(data.password());
        try {
            User user = new User();
            user.setEmail(email);
            user.setPassword(encryptedPassword);
            user.setFirstName(data.firstName());
            user.setLastName(data.lastName());
            user.setCep(data.cep());

            return userRepository.save(user);
        } catch (Exception e) {
            throw new ClientException("Error creating user", e.getMessage());
        }
    }

    @Transactional
    public User updateUser(Long id, UpdateUserRequest data) {

        User user = this.getById(id);

        if (isNotBlank(data.firstName())) {
            user.setFirstName(data.firstName());
        }

        if (isNotBlank(data.lastName())) {
            user.setLastName(data.lastName());
        }

        if (isNotBlank(data.cep())) {
            user.setCep(data.cep());
        }

        return userRepository.save(user);
    }

    /**
     * Soft-deletes a user by identifier.
     *
     * @param id the user identifier
     */
    @Transactional
    public void deleteUser(Long id) {
        userRepository.delete(this.getById(id));
    }

    /**
     * Validates if a user already exists with the given email.
     *
     * @param email the user data to validate
     *
     * @throws ConflictException if the user already exists
     */
    private void validateUserExists(String email) {
        userRepository.findByEmail(email)
            .ifPresent(user -> {
                throw new ConflictException("Email already in use");
            });
    }
}
