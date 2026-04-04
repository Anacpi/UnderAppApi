package com.tcc.underapp_api.database.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tcc.underapp_api.database.models.User;

/**
 * Repository for user persistence operations.
 * Provides CRUD access and custom user lookup methods.
 */
public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * Finds a user by email address.
     *
     * @param email the email address to search for
     * @return an Optional containing the user when found
     */
    Optional<User> findByEmail(String email);
}
