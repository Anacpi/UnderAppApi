package com.tcc.underapp_api.modules.user.enums;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

/**
 * Enumerates the supported user roles and their granted authorities.
 */
public enum UserRole {
    USER("user"),
    PUBLIC_AUTHORITY("public_authority"),
    ADMIN("admin");

    private final String role;

    UserRole(String role) {
        this.role = role;
    }

    /**
     * Returns the serialized role value.
     *
     * @return the role value
     */
    public String getRole() {
        return role;
    }

    /**
     * Returns the granted authorities associated with the role.
     *
     * @return the list of granted authorities
     */
    public List<GrantedAuthority> getAuthorities() {
        return switch (this) {
            case ADMIN -> List.of(
                    new SimpleGrantedAuthority("ROLE_ADMIN"),
                    new SimpleGrantedAuthority("ROLE_PUBLIC_AUTHORITY"),
                    new SimpleGrantedAuthority("ROLE_USER")
            );
            case PUBLIC_AUTHORITY -> List.of(
                    new SimpleGrantedAuthority("ROLE_PUBLIC_AUTHORITY"),
                    new SimpleGrantedAuthority("ROLE_USER")
            );
            case USER -> List.of(new SimpleGrantedAuthority("ROLE_USER"));
        };
    }
}
