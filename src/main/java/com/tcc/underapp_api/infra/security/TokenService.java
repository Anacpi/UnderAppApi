package com.tcc.underapp_api.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.tcc.underapp_api.database.models.User;

import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Service responsible for generating and validating JWT tokens.
 */
@Service
public class TokenService {

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private Long expiration;

    /**
     * Generates a signed JWT for the provided user.
     *
     * @param user the authenticated user
     * @return the signed JWT
     */
    public String generateToken(User user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secretKey);

            Map<String, Object> extraClaims = new HashMap<>();
            extraClaims.put("id", user.getId());
            extraClaims.put("email", user.getEmail());
            extraClaims.put("firstName", user.getFirstName());
            extraClaims.put("lastName", user.getLastName());
            extraClaims.put("role", user.getRole().name());
            extraClaims.put("cep", user.getCep());

            return JWT.create()
                    .withPayload(extraClaims)
                    .withIssuer("underapp-api")
                    .withSubject(String.valueOf(user.getId()))
                    .withIssuedAt(new Date(Instant.now().toEpochMilli()))
                    .withExpiresAt(this.generateExpirationDate())
                    .sign(algorithm);
        } catch (JWTCreationException e) {
            throw new RuntimeException("Error generating token", e);
        }
    }

    /**
     * Generates the token expiration date based on the configured expiration time.
     *
     * @return the token expiration date
     */
    private Date generateExpirationDate() {
        return new Date(Instant.now().toEpochMilli() + this.expiration);
    }

    /**
     * Validates a token and returns its decoded claims.
     *
     * @param token the JWT to validate
     * @return the decoded JWT claims
     */
    public DecodedJWT getTokenClaims(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secretKey);
    
            return JWT.require(algorithm)
                    .withIssuer("underapp-api")
                    .build()
                    .verify(token);
    
        } catch (JWTVerificationException e) {
            throw new RuntimeException("Invalid or expired token", e);
        }
    }
}
