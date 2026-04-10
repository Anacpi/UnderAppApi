package com.tcc.underapp_api.modules.user.service;

import com.tcc.underapp_api.common.errors.ClientException;
import com.tcc.underapp_api.common.errors.ConflictException;
import com.tcc.underapp_api.common.errors.NotFoundException;
import com.tcc.underapp_api.database.models.User;
import com.tcc.underapp_api.database.repository.UserRepository;
import com.tcc.underapp_api.integration.cloudinary.CloudinaryIntegration;
import com.tcc.underapp_api.modules.user.dto.request.CreateUserRequest;
import com.tcc.underapp_api.modules.user.dto.request.UpdateUserRequest;
import com.tcc.underapp_api.modules.user.dto.response.UserResponse;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

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
    private final CloudinaryIntegration cloudinaryIntegration;

    /**
     * Gets a user by their identifier.
     *
     * @param id the user identifier
     * @return the found user
     * @throws NotFoundException if the user is not found
     */
    public User getById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found", "Usuário não encontrado."));
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
            .orElseThrow(() -> new NotFoundException("User not found", "Usuário não encontrado."));
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
            throw new ClientException("Error creating user: " + e.getMessage(), "Não foi possível criar o usuário.", e);
        }
    }

    /**
     * Updates the user's profile data.
     *
     * Only non-blank fields provided in the request are updated.
     *
     * @param id the user identifier
     * @param data the data containing the fields to update
     *
     * @return the updated user
     *
     * @throws NotFoundException if the user is not found
     */
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
                throw new ConflictException("Email already in use", "Este e-mail já está em uso.");
            });
    }

    /**
     * Uploads a profile image for the specified user.
     *
     * The image is uploaded to Cloudinary as a private (authenticated) resource.
     * The generated public_id is stored in the user entity, and a signed URL
     * is returned to allow access to the image from the frontend.
     *
     * @param userId the identifier of the user uploading the image
     * @param image the image file to upload (multipart)
     *
     * @return a signed URL that allows access to the uploaded image
     *
     * @throws ClientException if the image is invalid, too large, or upload fails
     * @throws NotFoundException  if the user is not found
     */
    @Transactional
    public String uploadProfileImage(Long userId, MultipartFile image) {
        if (image == null) {
            throw new ClientException("Image file is null", "Envie uma imagem para continuar.");
        }

        // Validação de tipo de arquivo
        String contentType = image.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            throw new ClientException("Invalid file type. Only images are allowed.", "Envie um arquivo de imagem válido.");
        }

        // Validação de tamanho
        if (image.getSize() > 5 * 1024 * 1024) { // 5MB
            throw new ClientException("File size exceeds limit (5MB).", "A imagem deve ter no máximo 5MB.");
        }

        User user = getById(userId);

        // Upload para Cloudinary
        try {
            String imagePublicId = cloudinaryIntegration.uploadProfileImage(image, userId);
            updateProfileImage(user, imagePublicId);
            return cloudinaryIntegration.generatePrivateImageUrl(imagePublicId);
        } catch (IOException e) {
            throw new ClientException("Failed to upload profile image: " + e.getMessage(),
                    "Não foi possível enviar a imagem de perfil.", e);
        }
    }

    /**
     * Updates the user's profile image reference.
     *
     * Stores the Cloudinary public_id in the user entity for later retrieval
     * and generation of signed URLs.
     *
     * @param user the user to update
     * @param imagePublicId the Cloudinary public_id of the uploaded image
     */
    private void updateProfileImage(User user, String imagePublicId) {
        user.setProfileImagePublicId(imagePublicId);
        userRepository.save(user);
    }

    /**
     * Removes the user's profile image.
     *
     * Deletes the image from Cloudinary using its public_id and clears the
     * reference in the user entity.
     *
     * @param userId the identifier of the user
     *
     * @throws NotFoundException if the user is not found
     * @throws ClientException if the image deletion fails
     */
    @Transactional
    public void removeProfileImage(Long userId) {
        User user = getById(userId);

        if (user.getProfileImagePublicId() != null) {
            try {
                cloudinaryIntegration.deleteImage(user.getProfileImagePublicId());
            } catch (IOException e) {
                throw new ClientException("Failed to delete profile image: " + e.getMessage(),
                        "Não foi possível remover a imagem de perfil.", e);
            }

            user.setProfileImagePublicId(null);
            userRepository.save(user);
        }
    }

    /**
     * Builds a UserResponse from the given user entity.
     *
     * Generates a signed URL for the user's profile image if it exists,
     * allowing secure access to the image from the frontend.
     *
     * @param user the user entity
     *
     * @return a UserResponse containing the user's data and profile image URL
     */
    public UserResponse getUserResponse(User user) {
        String imageUrl = null;

        if (user.getProfileImagePublicId() != null) {
            imageUrl = cloudinaryIntegration
                    .generatePrivateImageUrl(user.getProfileImagePublicId());
        }

        return UserResponse.fromEntity(user, imageUrl);
    }
}
