package com.tcc.underapp_api.modules.auth.dto.Response;

/**
 * Response record returned after a successful authentication.
 *
 * @param token the generated JWT access token
 */
public record LoginResponse(String token) {}
