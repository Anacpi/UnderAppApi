package com.tcc.underapp_api.integration.cloudinary;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

/**
 * Service that handles image uploads to Cloudinary.
 * The destination folder is configurable via application.properties.
 */
@Service
@RequiredArgsConstructor
public class CloudinaryIntegration {

    private final Cloudinary cloudinary;

    @Value("${cloudinary.folder.profile-images}")
    private String profileImagesFolder;

    /**
     * Uploads a profile image for a specific user to the configured Cloudinary folder.
     *
     * The image is stored as a private (authenticated) resource using a deterministic
     * public_id based on the user identifier. If a profile image already exists for
     * the user, it is overwritten.
     *
     * @param file the image file to upload (multipart)
     * @param userId the identifier of the user
     *
     * @return the public_id of the uploaded image
     *
     * @throws IOException if the file reading or upload fails
     */
    public String uploadProfileImage(MultipartFile file, Long userId) throws IOException {

        String publicId = "users/" + userId + "/profile";

        Map<?, ?> uploadResult = cloudinary.uploader().upload(
                file.getBytes(),
                ObjectUtils.asMap(
                        "public_id", publicId,
                        "folder", profileImagesFolder,
                        "resource_type", "image",
                        "type", "authenticated",
                        "overwrite", true,
                        "invalidate", true
                )
        );
        return uploadResult.get("public_id").toString();
    }

    /**
     * Generates a signed URL to access a private (authenticated) image.
     *
     * @param publicId the Cloudinary public_id of the image
     *
     * @return a signed URL that allows access to the image
     */
    public String generatePrivateImageUrl(String publicId) {
        return cloudinary.url()
                .resourceType("image")
                .type("authenticated")
                .publicId(publicId)
                .signed(true)
                .generate();
    }

    /**
     * Deletes an image from Cloudinary using its public_id.
     *
     * @param publicId the Cloudinary public_id of the image
     *
     * @throws IOException if the deletion fails
     */
    public void deleteImage(String publicId) throws IOException {
        cloudinary.uploader().destroy(
                publicId,
                ObjectUtils.asMap(
                        "resource_type", "image",
                        "type", "authenticated",
                        "invalidate", true
                )
        );
    }
}
