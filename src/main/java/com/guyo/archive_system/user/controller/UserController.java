package com.guyo.archive_system.user.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.guyo.archive_system.user.dto.UserDto;
import com.guyo.archive_system.user.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public List<UserDto> getAll() {
        return userService.getAll();
    }

    @GetMapping("/{userSub}")
    public UserDto getById(@PathVariable UUID userSub) {
        return userService.getById(userSub);
    }

    /**
     * Blocks the account from being used without removing the record.
     * Distinct from {@link #delete}. TODO(security): gate to ADMIN once
     * per-endpoint role enforcement is in place.
     */
    @PostMapping("/{userSub}/deactivate")
    public UserDto deactivate(
            @PathVariable UUID userSub,
            @AuthenticationPrincipal Jwt jwt) {

        return userService.deactivate(
                userSub,
                UUID.fromString(jwt.getSubject())
        );
    }

    @PostMapping("/{userSub}/activate")
    public UserDto activate(
            @PathVariable UUID userSub,
            @AuthenticationPrincipal Jwt jwt) {

        return userService.activate(
                userSub,
                UUID.fromString(jwt.getSubject())
        );
    }

    /**
     * Soft-deletes the user record itself. For routine "this person
     * shouldn't be able to log in right now", use {@link #deactivate}
     * instead — this is for actual removal.
     */
    @DeleteMapping("/{userSub}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(
            @PathVariable UUID userSub,
            @AuthenticationPrincipal Jwt jwt) {

        userService.delete(
                userSub,
                UUID.fromString(jwt.getSubject())
        );
    }

    @PostMapping("/{userSub}/restore")
    public UserDto restore(
            @PathVariable UUID userSub,
            @AuthenticationPrincipal Jwt jwt) {

        return userService.restore(
                userSub,
                UUID.fromString(jwt.getSubject())
        );
    }
}