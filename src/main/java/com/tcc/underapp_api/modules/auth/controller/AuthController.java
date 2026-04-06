package com.tcc.underapp_api.modules.auth.controller;

import com.tcc.underapp_api.common.records.ApiResponse;
import com.tcc.underapp_api.database.models.User;
import com.tcc.underapp_api.modules.auth.dto.Request.LoginRequest;
import com.tcc.underapp_api.modules.auth.dto.Response.LoginResponse;
import com.tcc.underapp_api.modules.auth.dto.Request.RegisterRequest;
import com.tcc.underapp_api.modules.auth.service.AuthService;
import com.tcc.underapp_api.modules.user.dto.response.UserResponse;

import com.tcc.underapp_api.modules.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller responsible for authentication endpoints.
 * Exposes login and registration operations.
 */
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final UserService userService;

    /**
     * Authenticates a user and returns an access token.
     *
     * @param data the login request data
     * @return the API response containing the generated token
     */
    @PostMapping("/login")
    public ApiResponse<LoginResponse> login(@RequestBody @Valid LoginRequest data) {
        String token = authService.login(data);
        return ApiResponse.success(new LoginResponse(token));
    }

    /**
     * Registers a new user account.
     *
     * @param data the registration request data
     * @return the API response containing the created user data
     */
    @PostMapping("/register")
    public ApiResponse<UserResponse> register(@RequestBody @Valid RegisterRequest data) {
        User user = authService.register(data);
        return ApiResponse.success(userService.getUserResponse(user));
    }
}
