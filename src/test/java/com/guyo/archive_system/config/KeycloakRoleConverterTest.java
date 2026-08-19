package com.guyo.archive_system.config;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

class KeycloakRoleConverterTest {

    private final KeycloakRoleConverter converter = new KeycloakRoleConverter();

    @Test
    void convertsScopesRealmRolesAndApplicationClientRoles() {

        Jwt jwt = Jwt.withTokenValue("token")
                .header("alg", "none")
                .claim("scope", "profile email")
                .claim("realm_access", Map.of("roles", List.of("auditor")))
                .claim(
                        "resource_access",
                        Map.of(
                                "archive-system",
                                Map.of("roles", List.of("archive_officer"))
                        )
                )
                .build();

        Set<String> authorities = converter.convert(jwt).stream()
                .map(GrantedAuthority::getAuthority)
                .collect(java.util.stream.Collectors.toSet());

        assertThat(authorities).contains(
                "SCOPE_profile",
                "SCOPE_email",
                "ROLE_AUDITOR",
                "ROLE_ARCHIVE_OFFICER"
        );
    }
}
