package com.tcc.underapp_api.infra.config;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class that provides the Cloudinary bean for image uploads.
 * Credentials are read from application.properties.
 */
@Configuration
public class CloudinaryConfiguration {

    @Value("${cloudinary.api-name}")
    private String cloudName;

    @Value("${cloudinary.api-key}")
    private String apiKey;

    @Value("${cloudinary.api-secret}")
    private String apiSecret;

    /**
     * Creates a configured Cloudinary instance.
     *
     * @return Cloudinary object ready for upload operations
     */
    @Bean
    public Cloudinary cloudinary() {
        return new Cloudinary(ObjectUtils.asMap(
                "cloud_name", cloudName,
                "api_key", apiKey,
                "api_secret", apiSecret
        ));
    }
}