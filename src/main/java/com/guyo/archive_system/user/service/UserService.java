package com.guyo.archive_system.user.service;

import java.util.List;
import java.util.UUID;

import com.guyo.archive_system.user.dto.UserDto;

public interface UserService {

    UserDto getById(UUID userSub);

    List<UserDto> getAll();

}