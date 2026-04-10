package com.tcc.underapp_api.modules.auth.service;

import com.tcc.underapp_api.common.errors.UnauthorizedExcepition;
import com.tcc.underapp_api.database.models.User;
import com.tcc.underapp_api.infra.security.TokenService;
import com.tcc.underapp_api.modules.auth.dto.Request.LoginRequest;
import com.tcc.underapp_api.modules.auth.dto.Request.RegisterRequest;
import com.tcc.underapp_api.modules.user.dto.request.CreateUserRequest;
import com.tcc.underapp_api.modules.user.service.UserService;

import lombok.AllArgsConstructor;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Service responsible for authentication workflows such as login and registration.
 */
@Service
@AllArgsConstructor
public class AuthService {

    private final  UserService userService;
    private final TokenService tokenService;
    private final PasswordEncoder passwordEncoder;

    /**
     * Authenticates a user with email and password.
     *
     * @param data the login request data
     * @return the generated JWT token
     */
    public String login(LoginRequest data) {
        try {
            User user = userService.getByEmail(data.email());

            if (!passwordEncoder.matches(data.password(), user.getPassword())) {
                throw new UnauthorizedExcepition("Invalid credentials", "E-mail ou senha inválidos.");
            }

            return tokenService.generateToken(user);
        } catch (Exception e) {
            throw new UnauthorizedExcepition("Invalid credentials", "E-mail ou senha inválidos.");
        }
    }

    /**
     * Registers a new user in the system.
     *
     * @param data the registration request data
     * @return the created user entity
     */
    public User register(RegisterRequest data) {
        return userService.createUser(
                new CreateUserRequest(
                        data.email(),
                        data.password(),
                        data.firstName(),
                        data.lastName(),
                        data.cep()
                )
        );
    }
}
