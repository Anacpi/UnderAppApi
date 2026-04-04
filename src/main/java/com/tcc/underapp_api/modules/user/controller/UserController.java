package com.tcc.underapp_api.modules.user.controller;

import com.tcc.underapp_api.common.records.ApiResponse;
import com.tcc.underapp_api.modules.user.dto.request.UpdateUserRequest;
import com.tcc.underapp_api.modules.user.dto.response.UserResponse;
import com.tcc.underapp_api.modules.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

/**
 * Controller responsible for authenticated user profile operations.
 */
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    /**
     * Returns the authenticated user's profile.
     *
     * @param id the authenticated user id
     * @return the API response containing the user data
     */
    @GetMapping("/me")
    public ApiResponse<UserResponse> getMe(@AuthenticationPrincipal Long id) {
        return ApiResponse.success(UserResponse.fromEntity(userService.getById(id)));
    }

    /**
     * Updates the authenticated user's profile data.
     *
     * @param id the authenticated user id
     * @param data the fields to update
     * @return the API response containing the updated user data
     */
    @PutMapping("/me")
    public ApiResponse<UserResponse> updateMe(
            @AuthenticationPrincipal Long id,
            @RequestBody @Valid UpdateUserRequest data
    ) {
        var updatedUser = userService.updateUser(id, data);
        return ApiResponse.success(UserResponse.fromEntity(updatedUser));
    }

    /**
     * Soft-deletes the authenticated user account.
     *
     * @param id the authenticated user id
     * @return the API response indicating successful deletion
     */
    @DeleteMapping("/me")
    public ApiResponse<Void> deleteMe(@AuthenticationPrincipal Long id) {
        userService.deleteUser(id);
        return ApiResponse.success(null);
    }
}

