package com.tcc.underapp_api.infra.security;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.tcc.underapp_api.modules.user.enums.UserRole;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * Security filter responsible for authenticating requests based on JWT bearer tokens.
 * When a valid token is provided, the authenticated user is stored in the security context.
 */
@Component
public class SecurityFilter extends OncePerRequestFilter {

    private final TokenService tokenService;

    public SecurityFilter(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    /**
     * Intercepts HTTP requests, validates the bearer token, and populates the security context.
     *
     * @param request the current HTTP request
     * @param response the current HTTP response
     * @param filterChain the remaining filter chain
     * @throws ServletException if request processing fails
     * @throws IOException if an input or output error occurs
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String token = recoverToken(request);

        if (token != null) {
            try {
                DecodedJWT decodedJWT = tokenService.getTokenClaims(token);

                Long id = Long.parseLong(decodedJWT.getSubject());
                String role = decodedJWT.getClaim("role").asString();

                UserRole userRole = UserRole.valueOf(role.toUpperCase());

                var authorities = userRole.getAuthorities();
                var authentication = new UsernamePasswordAuthenticationToken(id, null, authorities);

                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (Exception e) {
                SecurityContextHolder.clearContext();
            }
        }
        filterChain.doFilter(request, response);
    }

    /**
     * Extracts the bearer token from the Authorization header.
     *
     * @param request the current HTTP request
     * @return the extracted token or null when the header is missing
     */
    private String recoverToken(HttpServletRequest request) {
        var authHeader = request.getHeader("Authorization");
        if (authHeader == null) return null;
        return authHeader.replace("Bearer ", "");
    }
}
