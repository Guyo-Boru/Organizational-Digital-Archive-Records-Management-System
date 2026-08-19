package com.guyo.archive_system.config;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;

/**
 * Converts Keycloak realm roles and this application's client roles into
 * Spring Security {@code ROLE_*} authorities while retaining OAuth scopes.
 */
public class KeycloakRoleConverter
        implements Converter<Jwt, Collection<GrantedAuthority>> {

    private static final String CLIENT_ID = "archive-system";

    private final JwtGrantedAuthoritiesConverter scopeConverter =
            new JwtGrantedAuthoritiesConverter();

    @Override
    public Collection<GrantedAuthority> convert(Jwt jwt) {

        Set<GrantedAuthority> authorities = new LinkedHashSet<>(
                scopeConverter.convert(jwt)
        );

        extractRoles(jwt.getClaim("realm_access"))
                .forEach(role -> authorities.add(
                        new SimpleGrantedAuthority("ROLE_" + role)
                ));

        Map<String, Object> resourceAccess = jwt.getClaim("resource_access");
        if (resourceAccess != null) {
            extractRoles(resourceAccess.get(CLIENT_ID))
                    .forEach(role -> authorities.add(
                            new SimpleGrantedAuthority("ROLE_" + role)
                    ));
        }

        return authorities;
    }

    @SuppressWarnings("unchecked")
    private Set<String> extractRoles(Object accessClaim) {

        if (!(accessClaim instanceof Map<?, ?> access)) {
            return Set.of();
        }

        Object rolesClaim = access.get("roles");
        if (!(rolesClaim instanceof Collection<?> roles)) {
            return Set.of();
        }

        Set<String> result = new LinkedHashSet<>();
        for (Object role : roles) {
            if (role instanceof String name && !name.isBlank()) {
                result.add(name.toUpperCase());
            }
        }

        return result;
    }
}
