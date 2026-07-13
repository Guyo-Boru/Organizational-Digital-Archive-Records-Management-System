package com.digitalarchive.domain.entity;

import jakarta.persistence.*;
import lombok.*;

/**
 * Local profile cache synced from Keycloak (see slide 8).
 * Deliberately does NOT store passwords, roles, or permissions —
 * those live in Keycloak only. user_sub is the Keycloak subject (JWT "sub" claim).
 */
@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppUser {

    @Id
    @Column(name = "user_sub", updatable = false)
    private String userSub;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(name = "email", nullable = false)
    private String email;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id")
    private Department department;
}
