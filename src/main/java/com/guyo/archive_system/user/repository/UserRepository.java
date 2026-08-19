package com.guyo.archive_system.user.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.guyo.archive_system.user.entity.User;

public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByUserSubAndDeletedAtIsNull(UUID userSub);

    List<User> findByDeletedAtIsNull();

}