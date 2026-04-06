package com.tcc.underapp_api.modules.user.controller;

import com.tcc.underapp_api.common.records.ApiResponse;
import com.tcc.underapp_api.modules.user.dto.request.UpdateUserRequest;
import com.tcc.underapp_api.modules.user.dto.response.UserResponse;
import com.tcc.underapp_api.modules.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

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
     *
     * @return the API response containing the user data
     */
    @GetMapping("/me")
    public ApiResponse<UserResponse> getMe(@AuthenticationPrincipal Long id) {
        var user = userService.getById(id);
        return ApiResponse.success(userService.getUserResponse(user));
    }

    /**
     * Updates the authenticated user's profile data.
     *
     * @param id the authenticated user id
     * @param data the fields to update
     *
     * @return the API response containing the updated user data
     */
    @PutMapping("/me")
    public ApiResponse<UserResponse> updateMe(
            @AuthenticationPrincipal Long id,
            @RequestBody @Valid UpdateUserRequest data
    ) {
        var updatedUser = userService.updateUser(id, data);
        return ApiResponse.success(userService.getUserResponse(updatedUser));
    }

    /**
     * Soft-deletes the authenticated user account.
     *
     * @param id the authenticated user id
     *
     * @return the API response indicating successful deletion
     */
    @DeleteMapping("/me")
    public ApiResponse<Void> deleteMe(@AuthenticationPrincipal Long id) {
        userService.deleteUser(id);
        return ApiResponse.success(null);
    }

    /**
     * Uploads a profile image for the authenticated user.
     * The image is sent to Cloudinary and the returned URL is stored in the user entity.
     *
     * @param id the authenticated user id
     * @param image the image file (multipart/form-data)
     *
     * @return API response containing the Cloudinary secure URL
     *
     * @throws RuntimeException if the file is empty or upload fails
     */
    @PostMapping(value = "/me/profile-image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<Map<String, String>> uploadProfileImage(
            @AuthenticationPrincipal Long id,
            @RequestParam("image") MultipartFile image
    ) {
        String imageUrl = userService.uploadProfileImage(id, image);
        return ApiResponse.success(Map.of("profileImageUrl", imageUrl));
    }

    /**
     * Deletes the profile image for the authenticated user.
     * Removes the image URL from the user entity.
     *
     * @param id the authenticated user id
     * @return API response indicating success
     */
    @DeleteMapping("/me/profile-image")
    public ApiResponse<Void> deleteProfileImage(@AuthenticationPrincipal Long id) {
        userService.removeProfileImage(id);
        return ApiResponse.success(null);
    }
}
