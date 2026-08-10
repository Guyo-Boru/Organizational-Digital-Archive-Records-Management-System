package com.guyo.archive_system.user.controller;

import com.guyo.archive_system.user.dto.UserDto;
import com.guyo.archive_system.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

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
}